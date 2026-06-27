package com.livefast.eattrash.rssgenerator.domain

import java.io.File

internal class FilePrinterImpl : FilePrinter {
    override fun execute(content: String) {
        val destination = File(OUTPUT_FILE_PATH)
        destination.parentFile?.mkdirs()
        destination.writer(charset = Charsets.UTF_8).use { writer ->
            writer.write(content)
        }
    }

    companion object {
        /**
         * Relative destination path where the RSS feed XML file is saved.
         */
        private const val OUTPUT_FILE_PATH = "docs/blog/rss.xml"
    }
}
