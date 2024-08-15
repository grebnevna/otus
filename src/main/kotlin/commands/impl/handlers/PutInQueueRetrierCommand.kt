package commands.impl.handlers

import commands.Command
import commands.impl.handlers.retry.RetryWithExceptionHandlingCommand
import java.util.*

class PutInQueueRetrierCommand(
    private val command: Command,
    private val exceptionCommand: Command,
    private val queue: Queue<Command>): Command {

    override fun execute() {
        queue.add(RetryWithExceptionHandlingCommand(command, exceptionCommand))
    }
}