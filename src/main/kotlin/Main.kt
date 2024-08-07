import commands.Command
import commands.impl.basic.rotate.RotateCommand
import commands.impl.basic.stop.StopCommand
import commands.impl.basic.stop.StopDoubleRetryCommand
import commands.impl.basic.stop.StopRetryCommand
import commands.impl.handlers.PutInQueueCommand
import commands.impl.handlers.WriteInLogCommand
import java.util.Queue
import java.util.concurrent.LinkedBlockingQueue

fun main() {

    ExceptionHandler.registerHandler(
        "StopCommand",
        "StopException"
    ) { _: Command, _: Exception, q: Queue<Command>? ->
        PutInQueueCommand(StopRetryCommand(), q!!)
    }

    ExceptionHandler.registerHandler(
        "StopRetryCommand",
        "StopException"
    ) { c: Command, e: Exception, q: Queue<Command>? ->
        PutInQueueCommand(StopDoubleRetryCommand(), q!!)
    }

    ExceptionHandler.registerHandler(
        "StopDoubleRetryCommand",
        "StopException"
    ) { c: Command, e: Exception, q: Queue<Command>? ->
        WriteInLogCommand(c, e)
    }

    ExceptionHandler.registerHandler(
        "RotateCommand",
        "RotateException"
    ) { c: Command, e: Exception, q: Queue<Command>? ->
        WriteInLogCommand(c, e)
    }

//    ExceptionHandler.registerHandler(
//        "RefuelCommand",
//        "RefuelException"
//    ) { c: Command, e: Exception ->
//        RefuelRetryCommand(c, e)
//    }
//
//    ExceptionHandler.registerHandler(
//        "RefuelRetryCommand",
//        "RefuelException"
//    ) { c: Command, e: Exception ->
//        RefuelDoubleRetryCommand(c, e)
//    }
//
//    ExceptionHandler.registerHandler(
//        "RefuelDoubleRetryCommand",
//        "RefuelException"
//    ) { c: Command, e: Exception ->
//        RefuelExceptionCommand(c, e)
//    }


    val queue: Queue<Command> = LinkedBlockingQueue()
    queue.add(StopCommand())
    queue.add(RotateCommand())


//    queue.add(RefuelCommand())

    queue.forEach { cmd ->
        try {
            cmd.execute()
        } catch (e: Exception) {
            ExceptionHandler.handle(cmd, e, queue).execute()
        }
    }

    while(true) {
        if (!queue.isEmpty()) {
            val cmd = queue.remove()

            try {
                cmd.execute()
            } catch (e: Exception) {
                ExceptionHandler.handle(cmd, e, queue).execute()
            }
        }
    }
}
