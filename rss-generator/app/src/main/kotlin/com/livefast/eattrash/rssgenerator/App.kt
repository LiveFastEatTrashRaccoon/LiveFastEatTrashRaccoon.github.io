package com.livefast.eattrash.rssgenerator

import com.livefast.eattrash.rssgenerator.core.data.PostRepository
import com.livefast.eattrash.rssgenerator.domain.FilePrinter
import com.livefast.eattrash.rssgenerator.domain.RssGenerator
import org.koin.java.KoinJavaComponent.inject

/**
 * Coordinator between the use cases to implement the application logic.
 */
interface App {
    /**
     * Runs the application logic.
     */
    suspend fun run()

    companion object {
        /**
         * Instance of the application.
         */
        val instance: App by inject(App::class.java)
    }
}

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
