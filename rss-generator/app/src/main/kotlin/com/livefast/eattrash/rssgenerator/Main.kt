package com.livefast.eattrash.rssgenerator

import com.livefast.eattrash.rssgenerator.core.di.coreModule
import com.livefast.eattrash.rssgenerator.data.di.dataModule
import com.livefast.eattrash.rssgenerator.domain.di.domainModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

/**
 * Application entry point.
 */
fun main() {
    setupDI()

    val app: App by inject(App::class.java)
    app.run()
}

private fun setupDI() {
    startKoin {
        modules(
            coreModule,
            dataModule,
            domainModule,
            appModule,
        )
    }
}
