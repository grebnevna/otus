package commands

import ExceptionHandler
import commands.impl.basic.move.MoveCommand
import commands.impl.basic.rotate.RotateCommand
import commands.impl.handlers.PutInQueueRetrierCommand
import commands.impl.handlers.WriteInLogCommand
import commands.impl.handlers.retry.RetryWithExceptionHandlingCommand
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

internal class PutInQueueRetrierCommandTest {

    @BeforeEach
    fun setUp() {
        ExceptionHandler.registerHandler(
            "MoveCommand",
            "MoveException"
        ) { c: Command, e: Exception, q: Queue<Command>? ->
            PutInQueueRetrierCommand(c, WriteInLogCommand(c, e), q!!)
        }

        ExceptionHandler.registerHandler(
            "RotateCommand",
            "RotateException"
        ) { c: Command, e: Exception, q: Queue<Command>? ->
            WriteInLogCommand(c, e)
        }
    }

    @Test
    fun `command repeater is added to queue`() {
        val queue: Queue<Command> = LinkedBlockingQueue()

        queue.add(MoveCommand())
        queue.add(RotateCommand())

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