package com.livefast.eattrash.rssgenerator

import com.livefast.eattrash.rssgenerator.data.PostRepository
import com.livefast.eattrash.rssgenerator.domain.FilePrinter
import com.livefast.eattrash.rssgenerator.domain.RssGenerator
import java.io.File

class App(
    private val repository: PostRepository,
    private val generator: RssGenerator,
    private val printer: FilePrinter,
) {
    fun run() {
        val posts = repository.getAll()
        val feedContent = generator.execute(posts = posts)
        val destination = File(OUTPUT_FILE)
        printer.execute(content = feedContent, destination = destination)
    }

    companion object {
        private const val OUTPUT_FILE = "docs/blog/rss.xml"
    }
}
