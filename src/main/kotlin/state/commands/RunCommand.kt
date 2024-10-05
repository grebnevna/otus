package state.commands

import commands.Command
import state.Context
import state.state.impl.CommonState

class RunCommand(private val context: Context): Command {
    override fun execute() {
        context.setHandlerState(CommonState())
    }
}