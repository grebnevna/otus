package multithreading.thread.impl

import commands.Command
import multithreading.thread.ServerThread

class ServerThreadImpl: ServerThread {
    private var command: Command? = null
    private var stop: Boolean = true

    private fun execute(executionCompleteCommand: Command?) {
        Thread {
            while (!stop) {
                this.command?.execute()
            }
            executionCompleteCommand?.execute()
        }.start()
    }

    override fun runExecution(command: Command, executionCompleteCommand: Command?) {
        setCommand(command)
        stop = false
        execute(executionCompleteCommand)
    }

    override fun stopExecution() {
        stop = true
    }

    override fun setCommand(command: Command) {
        this.command = command
    }
}