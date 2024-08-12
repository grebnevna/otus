package commands.impl.basic.move

import commands.Command
import models.Movable
import models.Vector

class MoveCommand(private val obj: Movable): Command {
    override fun execute() {
        val newPosition = Vector.plus(obj.getPosition(), obj.getVelocity())

        obj.setPosition(newPosition)
    }
}