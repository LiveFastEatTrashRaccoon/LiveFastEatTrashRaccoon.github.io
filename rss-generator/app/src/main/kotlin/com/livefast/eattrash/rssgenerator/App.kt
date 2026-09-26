package com.livefast.eattrash.rssgenerator

import com.livefast.eattrash.rssgenerator.core.data.PostRepository
import com.livefast.eattrash.rssgenerator.domain.FilePrinter
import com.livefast.eattrash.rssgenerator.domain.RssGenerator
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

/**
 * Coordinator between the use cases to implement the application logic.
 */
interface App {
    /**
     * Runs the application logic.
     */
    suspend fun run()
}

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
@Inject
internal class AppImpl(
    private val repository: PostRepository,
    private val generator: RssGenerator,
    private val printer: FilePrinter,
) : App {
    override suspend fun run() {
        val posts = repository.getAll()
        val feedContent = generator.execute(posts = posts)
        printer.execute(content = feedContent)
    }
}
