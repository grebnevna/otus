package state.state.impl

import state.queue.CommandQueueHandler
import state.state.State

class CommonState: State {

    override fun handle() {
        val cmd = CommandQueueHandler.queue.take()
        try {
            cmd.execute()
        } catch (e: Exception) {
            ExceptionHandler.handle(cmd, e, null).execute()
        }
    }
}