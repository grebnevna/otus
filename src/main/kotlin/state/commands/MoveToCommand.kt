package state.commands

import commands.Command
import state.Context
import state.state.impl.MoveToState

class MoveToCommand(private val context: Context): Command {

    override fun execute() {
        context.setHandlerState(MoveToState())
    }
}