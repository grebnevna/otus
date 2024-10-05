package state

import commands.Command
import state.state.State
import state.state.impl.CommonState

class Context {

    var state: State? = CommonState()
    fun setHandlerState(state: State?) {
        this.state = state
    }

    fun handle(executionCompleteCommand: Command?) {
        Thread {
            while (state !== null) {
                state?.handle()
            }
            executionCompleteCommand?.execute()
        }.start()
    }
}