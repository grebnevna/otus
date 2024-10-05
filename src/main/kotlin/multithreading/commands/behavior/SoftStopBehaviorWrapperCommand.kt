package multithreading.commands.behavior

import commands.Command
import multithreading.thread.ServerThread
import java.util.concurrent.LinkedBlockingQueue

class SoftStopBehaviorWrapperCommand(
    private val queue: LinkedBlockingQueue<Command>,
    private val serverThread: ServerThread
) : BehaviorCommand(queue) {
    override fun execute() {
        if (queue.size > 0) {
            super.execute()
        } else {
            serverThread.stopExecution()
        }
    }
}