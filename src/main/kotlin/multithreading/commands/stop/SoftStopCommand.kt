package multithreading.commands.stop

import commands.Command
import multithreading.commands.behavior.SoftStopBehaviorWrapperCommand
import multithreading.thread.ServerThread
import java.util.concurrent.LinkedBlockingQueue

class SoftStopCommand(
    private val queue: LinkedBlockingQueue<Command>,
    private val serverThread: ServerThread,
    ): Command {
    override fun execute() {
        serverThread.setCommand(SoftStopBehaviorWrapperCommand(queue, serverThread))
    }
}