package commands

import ExceptionHandler
import commands.impl.basic.move.MoveCommand
import commands.impl.handlers.WriteInLogCommand
import commands.impl.handlers.retry.RetryWithExceptionHandlingCommand
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

internal class RetryWithExceptionHandlingCommandTest {

    @BeforeEach
    fun setUp() {
        ExceptionHandler.registerHandler(
            "MoveCommand",
            "MoveException"
        ) { c: Command, e: Exception, q: Queue<Command>? ->
            RetryWithExceptionHandlingCommand(c, WriteInLogCommand(c, e))
        }
    }

    @Test
    fun `handler retries throwing exception command`() {
        val queue: Queue<Command> = LinkedBlockingQueue()
        val moveCommand = spy(MoveCommand())

        queue.add(moveCommand)

        try {
            moveCommand.execute()
        } catch (e: Exception) {
            ExceptionHandler.handle(moveCommand, e, null).execute()
        }

        verify(moveCommand, times(2)).execute()
    }
}