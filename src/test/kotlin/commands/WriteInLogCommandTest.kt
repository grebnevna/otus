package commands

import commands.impl.basic.move.MoveCommand
import commands.impl.handlers.WriteInLogCommand
import exceptions.MoveException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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
        val moveExceptionCommand = WriteInLogCommand(MoveCommand(), MoveException())
        moveExceptionCommand.execute()

        assertEquals("MoveException is intercepted during MoveCommand running\n", outContent.toString())
    }
}