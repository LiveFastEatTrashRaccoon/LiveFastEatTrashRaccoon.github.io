package com.livefast.eattrash.rssgenerator.domain

import java.io.File

/**
 * Use case responsible for writing generated content to a persistent file.
 */
interface FilePrinter {
    /**
     * Writes the provided string content to file.
     *
     * @param content text content to be written.
     */
    fun execute(content: String)
}

class FilePrinterImpl : FilePrinter {

    override fun execute(content: String) {
        val destination = File(OUTPUT_FILE_PATH)
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
