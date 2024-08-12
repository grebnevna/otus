package commands.impl.basic.common

import commands.Command
import commands.impl.MacroCommand
import commands.impl.basic.rotate.RotateCommand
import exceptions.CommandException
import models.Movable
import models.Rotable
import models.Velocity

class RotateChangingVelocityCommand<T>(obj: T): Command where T: Movable, T: Rotable, T: Velocity {
    private val changeVelocityCommand = ChangeVelocityCommand(obj)
    private val rotateCommand = RotateCommand(obj)

    private val macroCommand = MacroCommand(listOf(rotateCommand, changeVelocityCommand))

    override fun execute() {
        try {
            macroCommand.execute()
        } catch (e: Exception) {
            throw CommandException()
        }
    }
}