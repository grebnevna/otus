package models

// значения угла в градусах
interface Rotable {
    fun getAngle(): Double
    fun setAngle(value: Double)
    fun getAngularVelocity(): Double
}