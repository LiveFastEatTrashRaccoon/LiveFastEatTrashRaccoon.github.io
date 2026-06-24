package com.livefast.eattrash.rssgenerator.domain

import java.io.File

interface FilePrinter {
    fun execute(content: String, destination: File)
}

class FilePrinterImpl : FilePrinter {
    override fun execute(content: String, destination: File) {
        destination.writer(charset = Charsets.UTF_8).use { writer ->
            writer.write(content)
        }
    }
}
