package commands.impl.basic.fuel

import commands.Command
import models.FuelConsuming

class BurnFuelCommand(private val obj: FuelConsuming): Command {
    override fun execute() {
        println("BurnFuelCommand")
        val newFuelVolume = obj.getFuelVolume() - obj.getFuelDecrement()
        obj.setFuelVolume(newFuelVolume)
    }
}