package models

data class Vector(var x: Int, var y: Int) {
    companion object {
        fun plus(firstVector: Vector, secondVector: Vector): Vector {
            return Vector(
                firstVector.x + secondVector.x,
                firstVector.y + secondVector.y
            )
        }
    }
}