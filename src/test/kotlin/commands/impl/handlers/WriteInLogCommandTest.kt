package commands.impl.handlers

import commands.impl.basic.move.MoveCommand
import exceptions.MoveException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

internal class WriteInLogCommandTest {
    private val outContent: ByteArrayOutputStream = ByteArrayOutputStream()
    private val originalOut = System.out

    @BeforeEach
    fun setUpStream() {
        System.setOut(PrintStream(outContent))
    }

    @AfterEach
    fun restoreStream() {
        System.setOut(originalOut)
    }

    @Test
    fun `command execution should print correct line`() {
        val moveCommand = Mockito.mock(MoveCommand::class.java)

        val moveExceptionCommand = WriteInLogCommand(moveCommand, MoveException())
        moveExceptionCommand.execute()

        assertEquals("MoveException is intercepted during MoveCommand running\n", outContent.toString())
    }
}