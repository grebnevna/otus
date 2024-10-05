package multithreading.commands.stop

import commands.Command
import multithreading.thread.ServerThread

class HardStopCommand(private val serverThread: ServerThread): Command {
    override fun execute() {
        serverThread.stopExecution()
    }
}