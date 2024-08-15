package commands.impl.handlers.retry

import commands.Command

class RetryCommand(private val command: Command): Command {
    override fun execute() {
        command.execute()
    }
}