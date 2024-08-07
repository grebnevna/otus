package commands.impl.basic.stop

import commands.Command
import exceptions.StopException

class StopDoubleRetryCommand: Command {
    override fun execute() {
        println("StopDoubleRetryCommand")
        throw StopException()
    }
}