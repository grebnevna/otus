package commands.impl.basic.common

import commands.Command
import models.Movable
import models.Rotable
import models.Vector
import models.Velocity
import kotlin.math.*

class ChangeVelocityCommand<T>(private val obj: T): Command where T: Movable, T: Rotable, T: Velocity {

    override fun execute() {
        val x = obj.getVelocity().x.toDouble()
        val y = obj.getVelocity().y.toDouble()

        val linearVelocity = (x.pow(2.0) + y.pow(2.0)).pow(0.5)

        val angle = getAngle(x, y, linearVelocity)

        val changedAngle = angle + obj.getAngularVelocity()

        val newVector = Vector(
            round(linearVelocity * cos(changedAngle * PI / 180)).toInt(),
            round(linearVelocity * sin(changedAngle * PI / 180)).toInt()
        )

        obj.setVelocity(newVector)
    }

    private fun getAngle(x: Double, y: Double, linearVelocity: Double): Double {
        return if (x >= 0 && y >= 0)
            acos(x/linearVelocity) * 180 / PI
        else if (x < 0 && y >= 0) {
            acos(x/linearVelocity) * 180 / PI
        } else if (x < 0 && y < 0) {
            180 + acos((-1)*x/linearVelocity) * 180 / PI
        } else {
            asin(y/linearVelocity) * 180 / PI
        }
    }
}