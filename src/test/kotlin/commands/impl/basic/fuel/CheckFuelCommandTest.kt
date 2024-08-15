package commands.impl.basic.fuel

import exceptions.CommandException
import models.FuelConsuming
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

internal class CheckFuelCommandTest {

    @Test
    fun `exception would be thrown in case of FuelDecrement is greater then FuelVolume`() {
        val obj: FuelConsuming = mock(FuelConsuming::class.java)
        val command = CheckFuelCommand(obj)

        `when`(obj.getFuelVolume()).thenReturn(1)
        `when`(obj.getFuelDecrement()).thenReturn(2)

        assertThrows(CommandException::class.java) {
            command.execute()
        }
    }

    @Test
    fun `exception would not be thrown in case of FuelDecrement is equal to FuelVolume`() {
        val obj: FuelConsuming = mock(FuelConsuming::class.java)
        val command = CheckFuelCommand(obj)

        `when`(obj.getFuelVolume()).thenReturn(2)
        `when`(obj.getFuelDecrement()).thenReturn(2)

        assertDoesNotThrow {
            command.execute()
        }
    }
}