package state.state.impl

import state.queue.CommandQueueHandler
import state.queue.MoveToCommandQueueHandler
import state.state.State

class MoveToState: State {

    override fun handle() {
        if (CommandQueueHandler.queue.size > 0) {
            val cmd = CommandQueueHandler.queue.take()
            MoveToCommandQueueHandler.queue.add(cmd)
        }
    }
}