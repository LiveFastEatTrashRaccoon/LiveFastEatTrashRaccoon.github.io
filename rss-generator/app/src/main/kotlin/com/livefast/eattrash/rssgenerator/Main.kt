package com.livefast.eattrash.rssgenerator

import com.livefast.eattrash.rssgenerator.core.di.coreModule
import com.livefast.eattrash.rssgenerator.data.PostRepository
import com.livefast.eattrash.rssgenerator.data.di.dataModule
import com.livefast.eattrash.rssgenerator.domain.FilePrinter
import com.livefast.eattrash.rssgenerator.domain.RssGenerator
import com.livefast.eattrash.rssgenerator.domain.di.domainModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

/**
 * Application entry point.
 */
fun main() {
    setupDI()

    val repository: PostRepository by inject(PostRepository::class.java)
    val generator: RssGenerator by inject(RssGenerator::class.java)
    val printer: FilePrinter by inject(FilePrinter::class.java)

    App(
        repository = repository,
        generator = generator,
        printer = printer,
    ).run()
}

private fun setupDI() {
    startKoin {
        modules(
            coreModule,
            dataModule,
            domainModule,
        )
    }
}
