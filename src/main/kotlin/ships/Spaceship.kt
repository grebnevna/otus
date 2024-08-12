package ships

import models.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin

class Spaceship(
    private var position: Vector,
    private var velocity: Vector,

    private var direction: Int,
    private var directionsNumber: Int,
    private var angularIncrement: Int,

    private var fuelVolume: Int,
    private var fuelDecrement: Int,
) : Movable, Rotable, FuelConsuming, Velocity {

    override fun getPosition(): Vector {
        return position
    }

    override fun setPosition(value: Vector) {
        position = value
    }

//    override fun getVelocity(): Vector {
//        return Vector(
//            round(velocity * cos(getAngle() * PI / 180)).toInt(),
//            round(velocity * sin(getAngle() * PI / 180)).toInt()
//        )
//    }

    override fun getVelocity(): Vector {
        return velocity
    }

    override fun setVelocity(value: Vector) {
        velocity = value
    }

    override fun getAngle(): Double {
        return (360 * direction / directionsNumber).toDouble()
    }

    override fun setAngle(value: Double) {
        direction = round((value / (360 / directionsNumber))).toInt()
    }

    override fun getAngularVelocity(): Double {
        return (angularIncrement * (360 / directionsNumber)).toDouble()
    }

    override fun getFuelVolume(): Int {
        return fuelVolume
    }

    override fun setFuelVolume(value: Int) {
       this.fuelVolume = value
    }

    override fun getFuelDecrement(): Int {
        return fuelDecrement
    }
}