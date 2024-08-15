package commands.impl.basic.stop

import commands.Command
import exceptions.StopException


class StopCommand: Command {
    override fun execute() {
        println("StopCommand")
        throw StopException()
    }
}