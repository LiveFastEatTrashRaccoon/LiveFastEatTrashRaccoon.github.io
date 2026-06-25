package com.livefast.eattrash.rssgenerator

import com.livefast.eattrash.rssgenerator.data.PostRepository
import com.livefast.eattrash.rssgenerator.domain.FilePrinter
import com.livefast.eattrash.rssgenerator.domain.RssGenerator

class App(
    private val repository: PostRepository,
    private val generator: RssGenerator,
    private val printer: FilePrinter,
) {
    fun run() {
        val posts = repository.getAll()
        val feedContent = generator.execute(posts = posts)
        printer.execute(content = feedContent)
    }
}
