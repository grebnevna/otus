package commands.impl.basic.move

import commands.Command
import exceptions.MoveException

class MoveCommand: Command {
    override fun execute() {
        println("MoveCommand")
        throw MoveException()
    }
}