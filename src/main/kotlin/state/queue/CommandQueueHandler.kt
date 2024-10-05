package state.queue

import commands.Command
import java.util.concurrent.LinkedBlockingQueue

class CommandQueueHandler {
    companion object {
        val queue = LinkedBlockingQueue<Command>()
    }
}