package commands.impl.basic.rotate

import commands.Command
import exceptions.RotateException

class RotateCommand: Command {
    override fun execute() {
        println("RotateCommand")
        throw RotateException()
    }
}