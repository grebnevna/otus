package commands.impl.basic.fuel

import commands.Command
import exceptions.CommandException
import models.FuelConsuming

class CheckFuelCommand(private val obj: FuelConsuming): Command {
    override fun execute() {
        println("CheckFuelCommand")
        if (obj.getFuelVolume() - obj.getFuelDecrement() < 0)
            throw CommandException()
    }
}