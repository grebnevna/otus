package commands.impl.basic.common

import exceptions.CommandException
import models.FuelConsuming
import models.Movable
import models.Vector
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*

interface MovableAndFuelConsuming: FuelConsuming, Movable

internal class MoveWithFuelConsumptionCommandTest {

    @Test
    fun `if fuel volume is greater than fuel decrement then objects new fuel volume and position would be set`() {
        val obj: MovableAndFuelConsuming = mock(MovableAndFuelConsuming::class.java)

        val moveWithFuelConsumptionCommand = MoveWithFuelConsumptionCommand(obj)

        `when`(obj.getFuelVolume()).thenReturn(4)
        `when`(obj.getFuelDecrement()).thenReturn(2)
        `when`(obj.getPosition()).thenReturn(Vector(0, 0))
        `when`(obj.getVelocity()).thenReturn(Vector(1, 1))

        assertDoesNotThrow {
            moveWithFuelConsumptionCommand.execute()
        }
        verify(obj, times(1)).setFuelVolume(2)
        verify(obj, times(1)).setPosition(Vector(1, 1))
    }

    @Test
    fun `if fuel volume is less than fuel decrement then objects new fuel volume and position would not be set over exception throwing`() {
        val obj: MovableAndFuelConsuming = mock(MovableAndFuelConsuming::class.java)

        val moveWithFuelConsumptionCommand = MoveWithFuelConsumptionCommand(obj)

        `when`(obj.getFuelVolume()).thenReturn(2)
        `when`(obj.getFuelDecrement()).thenReturn(4)
        `when`(obj.getPosition()).thenReturn(Vector(0, 0))
        `when`(obj.getVelocity()).thenReturn(Vector(1, 1))

        assertThrows(CommandException::class.java) {
            moveWithFuelConsumptionCommand.execute()
        }

        verify(obj, times(0)).setFuelVolume(anyInt())
        verify(obj, times(0)).setPosition(Vector(1, 1))
    }
}