package commands.impl

import commands.Command
import exceptions.CommandException
import java.lang.Exception

class MacroCommand(private val commandList: List<Command>): Command {

    override fun execute() {
        for (i in commandList.indices)
            try {
                commandList[i].execute()
            } catch (e: Exception) {
                throw CommandException()
            }
    }
}