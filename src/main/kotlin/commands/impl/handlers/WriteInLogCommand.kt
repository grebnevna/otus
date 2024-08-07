package commands.impl.handlers

import commands.Command

class WriteInLogCommand(private val command: Command, private val exception: Exception): Command {

    override fun execute() {
        println("${exception::class.java.typeName.split(".").last()} is intercepted during ${command::class.java.typeName.split(".").last()} running")
    }
}