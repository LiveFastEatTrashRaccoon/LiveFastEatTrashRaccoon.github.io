package com.livefast.eattrash.rssgenerator.domain

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FilePrinterImplTest {
    private val sut = FilePrinterImpl()

    @Test
    fun `when executed, then the content is written to the destination file`() {
        val input = "fake-input"
        val output = "output.xml"

        sut.execute(content = input, destinationPath = output)

        val outputFile = File(output)
        assertTrue(outputFile.exists())
        val content = outputFile.readText()
        assertEquals(input, content)
        outputFile.delete()
    }
}
