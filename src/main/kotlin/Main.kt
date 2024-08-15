import commands.Command
import commands.impl.MacroCommand
import commands.impl.basic.common.ChangeVelocityCommand
import commands.impl.basic.rotate.RotateCommand
import commands.impl.handlers.WriteInLogCommand
import models.Vector
import ships.Spaceship
import java.util.Queue

fun main() {

    ExceptionHandler.registerHandler(
        "MacroCommand",
        "CommandException"
    ) { c: Command, e: Exception, _: Queue<Command>? ->
        WriteInLogCommand(c, e)
    }

//    ExceptionHandler.registerHandler(
//        "StopCommand",
//        "StopException"
//    ) { _: Command, _: Exception, q: Queue<Command>? ->
//        PutInQueueCommand(StopRetryCommand(), q!!)
//    }
//
//    ExceptionHandler.registerHandler(
//        "StopRetryCommand",
//        "StopException"
//    ) { c: Command, e: Exception, q: Queue<Command>? ->
//        PutInQueueCommand(StopDoubleRetryCommand(), q!!)
//    }
//
//    ExceptionHandler.registerHandler(
//        "StopDoubleRetryCommand",
//        "StopException"
//    ) { c: Command, e: Exception, q: Queue<Command>? ->
//        WriteInLogCommand(c, e)
//    }


//    val queue: Queue<Command> = LinkedBlockingQueue()
//    queue.add(StopCommand())
//    queue.add(RotateCommand())


//    queue.add(RefuelCommand())

//    queue.forEach { cmd ->
//        try {
//            cmd.execute()
//        } catch (e: Exception) {
//            ExceptionHandler.handle(cmd, e, queue).execute()
//        }
//    }
//
//    while(true) {
//        if (!queue.isEmpty()) {
//            val cmd = queue.remove()
//
//            try {
//                cmd.execute()
//            } catch (e: Exception) {
//                ExceptionHandler.handle(cmd, e, queue).execute()
//            }
//        }
//    }

    val spaceship = Spaceship(
        Vector(0, 0),
        Vector(10, 0),
        0,
        12,
        1,
        100,
        10
    )

    val changeVelocityCommand = ChangeVelocityCommand(spaceship)
    val rotateCommand = RotateCommand(spaceship)

    val macroCommand = MacroCommand(listOf(rotateCommand, changeVelocityCommand))

    macroCommand.execute()
    macroCommand.execute()
    macroCommand.execute()
    macroCommand.execute()
    macroCommand.execute()
    macroCommand.execute()
//    macroCommand.execute()
//    macroCommand.execute()
//    macroCommand.execute()
//    macroCommand.execute()


//    val checkFuelCommand = CheckFuelCommand(spaceship)
//    val burnFuelCommand = BurnFuelCommand(spaceship)
//    val moveCommand = MoveCommand(spaceship)
//
//    val macroCommand = MoveWithFuelConsumptionCommand(spaceship)
//
//    macroCommand.execute()
//    macroCommand.execute()
//    macroCommand.execute()
//    macroCommand.execute()
//    macroCommand.execute()
//    try {
//        macroCommand.execute()
//    } catch (e: Exception) {
//        ExceptionHandler.handle(macroCommand, e, null).execute()
//    }

}
