package commands.impl.basic.fuel

import models.FuelConsuming
import org.junit.jupiter.api.Test

import org.mockito.Mockito.*

internal class BurnFuelCommandTest {

    @Test
    fun `setFuelVolume would be invoked `() {
        val obj: FuelConsuming = mock(FuelConsuming::class.java)
        val command = BurnFuelCommand(obj)

        `when`(obj.getFuelVolume()).thenReturn(4)
        `when`(obj.getFuelDecrement()).thenReturn(2)

        command.execute()

        verify(obj, times(1)).setFuelVolume(2)
    }
}