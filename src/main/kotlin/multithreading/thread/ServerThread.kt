package multithreading.thread

import commands.Command

interface ServerThread {
    fun runExecution(command: Command, executionCompleteCommand: Command?)

    fun stopExecution()

    fun setCommand(command: Command)
}