package com.livefast.eattrash.rssgenerator.domain

/**
 * Use case responsible for writing generated content to a persistent file.
 */
interface FilePrinter {
    /**
     * Writes the provided string content to file.
     *
     * @param content text content to be written.
     */
    suspend fun execute(
        content: String,
        destinationPath: String = OUTPUT_FILE_PATH,
    )

    companion object {
        /**
         * Relative destination path where the RSS feed XML file is saved.
         */
        private const val OUTPUT_FILE_PATH = "docs/blog/rss.xml"
    }
}
