package commands.impl.handlers.retry

import commands.Command

class RetryWithExceptionHandlingCommand(private val command: Command, private val exceptionCommand: Command): Command {
    override fun execute() {
        try {
            command.execute()
        } catch (e: Exception) {
            exceptionCommand.execute()
        }

    }
}