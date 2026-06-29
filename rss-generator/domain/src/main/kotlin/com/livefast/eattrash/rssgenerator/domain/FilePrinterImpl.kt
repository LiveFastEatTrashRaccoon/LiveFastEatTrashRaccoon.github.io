package com.livefast.eattrash.rssgenerator.domain

import java.io.File

internal class FilePrinterImpl : FilePrinter {
    override fun execute(
        content: String,
        destinationPath: String,
    ) {
        val destination = File(destinationPath)
        destination.parentFile?.mkdirs()
        destination.writer(charset = Charsets.UTF_8).use { writer ->
            writer.write(content)
        }
    }
}
