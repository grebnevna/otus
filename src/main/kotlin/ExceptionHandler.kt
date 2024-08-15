import commands.Command
import java.util.*
import kotlin.Exception

class ExceptionHandler {
    companion object {
        private var store: MutableMap<String, MutableMap<String, (c: Command, e: Exception, q: Queue<Command>?) -> Command>> = mutableMapOf()

        fun handle(command: Command, exception: Exception, queue: Queue<Command>?): Command {
            val commandTypeName = command::class.java.typeName.split(".").last()
            val exceptionTypeName = exception::class.java.typeName.split(".").last()

            return (store[commandTypeName]!![exceptionTypeName]!!)(command, exception, queue)
        }

        fun registerHandler(
            commandTypeName: String,
            exceptionTypeName: String,
            handler: (c: Command, e: Exception, q: Queue<Command>?) -> Command
        ) {
            if (store[commandTypeName] == null) {
                store[commandTypeName] = mutableMapOf(exceptionTypeName to handler)
            } else {
                store[commandTypeName]!![exceptionTypeName] = handler
            }
        }
    }
}
