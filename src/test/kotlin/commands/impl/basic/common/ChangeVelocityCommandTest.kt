package commands.impl.basic.common

import models.Movable
import models.Rotable
import models.Vector
import models.Velocity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import org.mockito.Mockito.*

interface Ship: Movable, Rotable, Velocity
internal class ChangeVelocityCommandTest {

    private val obj: Ship = mock(Ship::class.java)
    private val changeVelocityCommand = ChangeVelocityCommand(obj)

    @BeforeEach
    fun setUp() {
        `when`(obj.getPosition()).thenReturn(Vector(0, 0))
    }

    // 1 случай: вектор скорости образует с осью OX угол от 0 до PI/2
    @Test
    fun `if velocity vector forms a 45 degree angle with the OX axis and angularVelocity is 45 deg then new velocity angle would be 90 deg`() {
        `when`(obj.getVelocity()).thenReturn(Vector(7, 7))
        `when`(obj.getAngularVelocity()).thenReturn(45.0)

        changeVelocityCommand.execute()

        verify(obj, times(1)).setVelocity(Vector(0, 10))
    }

    // 2 случай: вектор скорости образует с осью OX угол от PI/2 до PI
    @Test
    fun `if velocity vector forms a 135 degree angle with the OX axis and angularVelocity is 45 deg then new velocity angle would be 180 deg`() {
        `when`(obj.getVelocity()).thenReturn(Vector(-7, 7))
        `when`(obj.getAngularVelocity()).thenReturn(45.0)

        changeVelocityCommand.execute()

        verify(obj, times(1)).setVelocity(Vector(-10, 0))
    }

    // 3 случай: вектор скорости образует с осью OX угол от PI до 3PI/2
    @Test
    fun `if velocity vector forms a 225 degree angle with the OX axis and angularVelocity is 45 deg then new velocity angle would be 270 deg`() {
        `when`(obj.getVelocity()).thenReturn(Vector(-7, -7))
        `when`(obj.getAngularVelocity()).thenReturn(45.0)

        changeVelocityCommand.execute()

        verify(obj, times(1)).setVelocity(Vector(0, -10))
    }

    // 4 случай: вектор скорости образует с осью OX угол от 3PI/2 до 2PI
    @Test
    fun `if velocity vector forms a 315 degree angle with the OX axis and angularVelocity is 45 deg then new velocity angle would be 0 deg`() {
        `when`(obj.getVelocity()).thenReturn(Vector(7, -7))
        `when`(obj.getAngularVelocity()).thenReturn(45.0)

        changeVelocityCommand.execute()

        verify(obj, times(1)).setVelocity(Vector(10, 0))
    }
}