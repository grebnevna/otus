package commands.impl.handlers

import ExceptionHandler
import commands.Command
import commands.impl.basic.move.MoveCommand
import commands.impl.basic.stop.StopCommand
import commands.impl.basic.stop.StopDoubleRetryCommand
import commands.impl.basic.stop.StopRetryCommand
import exceptions.StopException
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.lang.RuntimeException
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.timerTask
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PutInQueueCommandTest {

    @Test
    fun `handler put writing log command in queue`() {

        ExceptionHandler.registerHandler(
            "MoveCommand",
            "RuntimeException"
        ) { c: Command, e: Exception, q: Queue<Command>? ->
            PutInQueueCommand(WriteInLogCommand(c, e), q!!)
        }

        val queue: Queue<Command> = LinkedBlockingQueue()

        val moveCommand = mock(MoveCommand::class.java)

        `when`(moveCommand.execute()).thenThrow(RuntimeException())

        queue.add(moveCommand)

        try {
            moveCommand.execute()
        } catch (e: Exception) {
            ExceptionHandler.handle(moveCommand, e, queue).execute()
        }

        assertEquals(queue.size, 2)
        assertTrue(queue.last() is WriteInLogCommand)
    }

    // задание 8 hw3
    @Test
    fun `first exception - retry command, second - write in log`() {
        val stopRetryCommand = spy(StopRetryCommand())
        val writeInLogForStopCommand = spy(WriteInLogCommand(stopRetryCommand, StopException()))

        ExceptionHandler.registerHandler(
            "StopCommand",
            "StopException"
        ) { _: Command, _: Exception, q: Queue<Command>? ->
            PutInQueueCommand(stopRetryCommand, q!!)
        }

        ExceptionHandler.registerHandler(
            "StopRetryCommand",
            "StopException"
        ) { c: Command, e: Exception, q: Queue<Command>? ->
            writeInLogForStopCommand
        }

        val queue: Queue<Command> = LinkedBlockingQueue()

        val stopCommand = spy(StopCommand())

        queue.add(stopCommand)

        var stop = false

        Timer().schedule(timerTask {
            stop = true
        }, 500)

        while(!stop) {
            if (!queue.isEmpty()) {
                val cmd = queue.remove()

                try {
                    cmd.execute()
                } catch (e: Exception) {
                    ExceptionHandler.handle(cmd, e, queue).execute()
                }
            }
        }

        verify(stopCommand, times(1)).execute()
        verify(stopRetryCommand, times(1)).execute()
        verify(writeInLogForStopCommand, times(1)).execute()
    }

    // задание 9 hw3
    @Test
    fun `retry command twice then write in log`() {
        val stopRetryCommand = spy(StopRetryCommand())
        val stopDoubleRetryCommand = spy(StopDoubleRetryCommand())
        val writeInLogForStopCommand = spy(WriteInLogCommand(stopDoubleRetryCommand, StopException()))

        ExceptionHandler.registerHandler(
            "StopCommand",
            "StopException"
        ) { _: Command, _: Exception, q: Queue<Command>? ->
            PutInQueueCommand(stopRetryCommand, q!!)
        }

        ExceptionHandler.registerHandler(
            "StopRetryCommand",
            "StopException"
        ) { c: Command, e: Exception, q: Queue<Command>? ->
            PutInQueueCommand(stopDoubleRetryCommand, q!!)
        }

        ExceptionHandler.registerHandler(
            "StopDoubleRetryCommand",
            "StopException"
        ) { _: Command, _: Exception, _: Queue<Command>? ->
            writeInLogForStopCommand
        }

        val queue: Queue<Command> = LinkedBlockingQueue()

        val stopCommand = spy(StopCommand())

        queue.add(stopCommand)

        var stop = false

        Timer().schedule(timerTask {
            stop = true
        }, 500)

        while(!stop) {
            if (!queue.isEmpty()) {
                val cmd = queue.remove()

                try {
                    cmd.execute()
                } catch (e: Exception) {
                    ExceptionHandler.handle(cmd, e, queue).execute()
                }
            }
        }

        verify(stopCommand, times(1)).execute()
        verify(stopRetryCommand, times(1)).execute()
        verify(stopDoubleRetryCommand, times(1)).execute()
        verify(writeInLogForStopCommand, times(1)).execute()
    }
}