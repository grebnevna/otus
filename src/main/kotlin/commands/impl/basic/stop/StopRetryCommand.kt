package commands.impl.basic.stop

import commands.Command
import exceptions.StopException

class StopRetryCommand: Command {
    override fun execute() {
        println("StopRetryCommand")
        throw StopException()
    }
}