package state

import commands.Command
import multithreading.commands.LatchCommand
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import state.commands.*
import state.queue.CommandQueueHandler
import state.queue.MoveToCommandQueueHandler
import state.state.impl.CommonState
import state.state.impl.MoveToState
import java.util.concurrent.CountDownLatch
import java.util.concurrent.LinkedBlockingQueue

internal class StatePatternTest {

    // выполняется после завершения цикла обработки команд очереди в потоке контекста
    private val commandThreadStopAlertCommand = Mockito.mock(LatchCommand::class.java)

    private lateinit var context: Context
    private lateinit var latch: CountDownLatch
    private lateinit var queue: LinkedBlockingQueue<Command>

    @BeforeEach
    fun setUp() {
        context = Context()
        latch = CountDownLatch(1)
        queue = CommandQueueHandler.queue

        Mockito.`when`(commandThreadStopAlertCommand.execute()).then { latch.countDown() }
    }

    @Test
    fun `checks HardStopCommand in CommonState - a commandThreadStopAlertCommand should be executed after HardStopCommand`() {
        val hardStopCommand = HardStopCommand(context)

        queue.add(hardStopCommand)

        context.handle(commandThreadStopAlertCommand)
        latch.await()

        Mockito.verify(commandThreadStopAlertCommand, Mockito.times(1)).execute()
    }

    @Test
    fun `checks HardStopCommand in MoveToState - a commandThreadStopAlertCommand should be executed after HardStopCommand`() {
        val moveToCommand = MoveToCommand(context)
        val hardStopCommand = HardStopCommand(context)

        queue.add(moveToCommand)
        queue.add(hardStopCommand)

        var additionalThreadStop = false
        val additionalQueue = MoveToCommandQueueHandler.queue
        Thread {
            while (!additionalThreadStop) {
                val cmd = additionalQueue.take()
                cmd.execute()
            }
        }.start()

        context.handle(commandThreadStopAlertCommand)
        latch.await()
        additionalThreadStop = true

        Mockito.verify(commandThreadStopAlertCommand, Mockito.times(1)).execute()
    }

    @Test
    fun `MoveTo state check - context state changes from CommonState to MoveToState after MoveToCommand execution`() {
        assert(context.state is CommonState)

        val countDownLatch = CountDownLatch(1)
        val moveToCommand = MoveToCommand(context)
        val latchCommand = LatchCommand(countDownLatch)

        queue.add(moveToCommand)
        queue.add(latchCommand)

        context.handle(commandThreadStopAlertCommand)

        var additionalThreadStop = false
        val additionalQueue = MoveToCommandQueueHandler.queue
        Thread {
            while (!additionalThreadStop) {
                val cmd = additionalQueue.take()
                cmd.execute()
            }
        }.start()

        countDownLatch.await()

        additionalThreadStop = true

        assert(context.state is MoveToState)
    }

    @Test
    fun `RunCommand check - context state changes from MoveToState to CommonState after RunCommand execution`() {
        context.setHandlerState(MoveToState())
        assert(context.state is MoveToState)

        var additionalThreadStop = false
        val additionalQueue = MoveToCommandQueueHandler.queue
        Thread {
            while (!additionalThreadStop) {
                val cmd = additionalQueue.take()
                cmd.execute()
            }
        }.start()

        val countDownLatch = CountDownLatch(1)

        val runCommand = RunCommand(context)
        val latchCommand = LatchCommand(countDownLatch)

        queue.add(runCommand)
        queue.add(latchCommand)

        context.handle(commandThreadStopAlertCommand)

        countDownLatch.await()

        additionalThreadStop = true

        assert(context.state is CommonState)
    }
}