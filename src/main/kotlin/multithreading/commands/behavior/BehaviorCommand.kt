package multithreading.commands.behavior

import commands.Command
import java.lang.Exception
import java.util.concurrent.LinkedBlockingQueue

open class BehaviorCommand(private val queue: LinkedBlockingQueue<Command>): Command {
    override fun execute() {
        val cmd = queue.take()
        try {
            cmd.execute()
        } catch (e: Exception) {
            ExceptionHandler.handle(cmd, e, null).execute()
        }
    }
}