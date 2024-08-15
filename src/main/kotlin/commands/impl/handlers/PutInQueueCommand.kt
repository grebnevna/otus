package commands.impl.handlers

import commands.Command
import java.util.*

class PutInQueueCommand(private val command: Command, private val queue: Queue<Command>): Command {

    override fun execute() {
        queue.add(command)
    }
}