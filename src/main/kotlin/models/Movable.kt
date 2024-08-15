package models

interface Movable {
    fun getPosition(): Vector
    fun setPosition(value: Vector)
    fun getVelocity(): Vector
}