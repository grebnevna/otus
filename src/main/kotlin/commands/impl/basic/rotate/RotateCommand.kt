package commands.impl.basic.rotate

import commands.Command
import exceptions.RotateException
import models.Rotable
import models.Vector

class RotateCommand(private val obj: Rotable): Command {
    override fun execute() {
        val newAngle = obj.getAngle() + obj.getAngularVelocity()

        obj.setAngle(newAngle)
    }
}