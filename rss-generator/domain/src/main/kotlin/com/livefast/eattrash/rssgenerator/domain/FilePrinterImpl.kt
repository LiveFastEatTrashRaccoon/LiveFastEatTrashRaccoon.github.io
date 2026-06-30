package com.livefast.eattrash.rssgenerator.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

internal class FilePrinterImpl : FilePrinter {
    override suspend fun execute(
        content: String,
        destinationPath: String,
    ) = withContext(Dispatchers.IO) {
        val destination = File(destinationPath)
        destination.parentFile?.mkdirs()
        destination.writer(charset = Charsets.UTF_8).use { writer ->
            writer.write(content)
        }
    }
}
