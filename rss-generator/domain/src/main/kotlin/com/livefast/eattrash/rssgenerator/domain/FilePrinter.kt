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
    fun execute(content: String)
}
