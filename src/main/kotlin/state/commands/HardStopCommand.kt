package state.commands

import commands.Command
import state.Context

class HardStopCommand(private val context: Context): Command {

    override fun execute() {
        context.setHandlerState(null)
    }
}