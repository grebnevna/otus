package commands.impl.basic.common

import commands.Command
import commands.impl.MacroCommand
import commands.impl.basic.fuel.BurnFuelCommand
import commands.impl.basic.fuel.CheckFuelCommand
import commands.impl.basic.move.MoveCommand
import exceptions.CommandException
import models.FuelConsuming
import models.Movable

class MoveWithFuelConsumptionCommand<T>(obj: T): Command where T: Movable, T: FuelConsuming {
    private val checkFuelCommand = CheckFuelCommand(obj)
    private val burnFuelCommand = BurnFuelCommand(obj)
    private val moveCommand = MoveCommand(obj)
    private val macroCommand = MacroCommand(listOf(checkFuelCommand, moveCommand, burnFuelCommand))

    override fun execute() {
        try {
            macroCommand.execute()
        } catch (e: Exception) {
            throw CommandException()
        }
    }
}