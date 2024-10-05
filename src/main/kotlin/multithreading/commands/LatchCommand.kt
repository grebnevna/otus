package multithreading.commands

import commands.Command
import java.util.concurrent.CountDownLatch

class LatchCommand(private val latch: CountDownLatch): Command {
    override fun execute() {
        latch.countDown()
    }
}