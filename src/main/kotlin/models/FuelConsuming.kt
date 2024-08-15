package models

interface FuelConsuming {
    fun getFuelVolume(): Int
    fun setFuelVolume(value: Int)
    fun getFuelDecrement(): Int
}