package commands.impl.handlers

import ExceptionHandler
import commands.Command
import commands.impl.basic.move.MoveCommand
import commands.impl.basic.rotate.RotateCommand
import commands.impl.handlers.retry.RetryWithExceptionHandlingCommand
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.lang.RuntimeException
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

internal class PutInQueueRetrierCommandTest {

    @BeforeEach
    fun setUp() {
        ExceptionHandler.registerHandler(
            "MoveCommand",
            "RuntimeException"
        ) { c: Command, e: Exception, q: Queue<Command>? ->
            PutInQueueRetrierCommand(c, WriteInLogCommand(c, e), q!!)
        }

        ExceptionHandler.registerHandler(
            "RotateCommand",
            "RuntimeException"
        ) { c: Command, e: Exception, q: Queue<Command>? ->
            WriteInLogCommand(c, e)
        }
    }

    @Test
    fun `command repeater is added to queue`() {
        val queue: Queue<Command> = LinkedBlockingQueue()

        val moveCommand = Mockito.mock(MoveCommand::class.java)
        val rotateCommand = Mockito.mock(RotateCommand::class.java)

        Mockito.`when`(moveCommand.execute()).thenThrow(RuntimeException())
        Mockito.`when`(rotateCommand.execute()).thenThrow(RuntimeException())

        queue.add(moveCommand)
        queue.add(rotateCommand)

        queue.forEach { cmd ->
            try {
                cmd.execute()
            } catch (e: Exception) {
                ExceptionHandler.handle(cmd, e, queue).execute()
            }
        }

        assertTrue(queue.last() is RetryWithExceptionHandlingCommand)
    }
}