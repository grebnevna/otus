package state.queue

import commands.Command
import java.util.concurrent.LinkedBlockingQueue

class MoveToCommandQueueHandler {
    companion object {
        val queue = LinkedBlockingQueue<Command>()
    }
}