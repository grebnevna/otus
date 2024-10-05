package multithreading

import commands.Command
import commands.impl.basic.move.MoveCommand
import commands.impl.basic.rotate.RotateCommand
import multithreading.commands.LatchCommand
import multithreading.commands.behavior.BehaviorCommand
import multithreading.commands.stop.HardStopCommand
import multithreading.commands.stop.SoftStopCommand
import multithreading.thread.ServerThread
import multithreading.thread.impl.ServerThreadImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.concurrent.CountDownLatch
import java.util.concurrent.LinkedBlockingQueue

internal class ServerThreadTest {

    private val moveCommand: MoveCommand = Mockito.mock(MoveCommand::class.java)
    private val rotateCommand: RotateCommand = Mockito.mock(RotateCommand::class.java)

    private var queue: LinkedBlockingQueue<Command> = LinkedBlockingQueue<Command>()
    private var behaviorCommand: Command = BehaviorCommand(queue)
    private var serverThread: ServerThread = ServerThreadImpl()

    private var hardStopCommand: Command = HardStopCommand(serverThread)
    private var softStopCommand: Command = SoftStopCommand(queue, serverThread)

    @BeforeEach
    fun setUp() {
        queue = LinkedBlockingQueue<Command>()
        behaviorCommand = BehaviorCommand(queue)
        serverThread = ServerThreadImpl()

        hardStopCommand = HardStopCommand(serverThread)
        softStopCommand = SoftStopCommand(queue, serverThread)
    }

    @Test
    fun `checks if thread starts`() {
        val latch = CountDownLatch(1)
        val latchCommand = LatchCommand(latch)

        queue.add(moveCommand)
        queue.add(latchCommand)

        serverThread.runExecution(behaviorCommand, null)

        latch.await()
        Mockito.verify(moveCommand, Mockito.times(1)).execute()
    }

    // проверяем что после жесткой остановки команда rotateCommand не выполняется
    @Test
    fun `checks hard stop command`() {
        val latch = CountDownLatch(1)
        val latchCommand = LatchCommand(latch)

        queue.add(moveCommand)
        queue.add(hardStopCommand)
        queue.add(rotateCommand)

        serverThread.runExecution(behaviorCommand, latchCommand)

        latch.await()
        Mockito.verify(moveCommand, Mockito.times(1)).execute()
        Mockito.verify(rotateCommand, Mockito.times(0)).execute()
    }

    // проверяем что выполняются команды после мягкой остановки и при этом вызывается
    // команда-хук (executionCompleteCommand) после остановки event-loop
    @Test
    fun `checks soft stop command`() {
        val latch = CountDownLatch(1)
        val latchCommand = LatchCommand(latch)

        val executionCompleteCommand: MoveCommand = Mockito.mock(MoveCommand::class.java)

        queue.add(moveCommand)
        queue.add(softStopCommand)
        queue.add(rotateCommand)
        queue.add(latchCommand)

        serverThread.runExecution(behaviorCommand, executionCompleteCommand)

        latch.await()
        Mockito.verify(moveCommand, Mockito.times(1)).execute()
        Mockito.verify(rotateCommand, Mockito.times(1)).execute()
        Mockito.verify(executionCompleteCommand, Mockito.times(1)).execute()

    }
}