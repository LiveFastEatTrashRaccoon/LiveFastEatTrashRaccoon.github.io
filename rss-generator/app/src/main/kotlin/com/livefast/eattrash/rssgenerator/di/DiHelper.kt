package com.livefast.eattrash.rssgenerator.di

import com.livefast.eattrash.rssgenerator.core.di.coreModule
import com.livefast.eattrash.rssgenerator.data.di.dataModule
import com.livefast.eattrash.rssgenerator.domain.di.domainModule
import org.koin.core.context.startKoin

/**
 * Helper object for DI setup.
 */
object DiHelper {
    /**
     * Set up the DI framework.
     */
    fun setup() {
        startKoin {
            modules(
                coreModule,
                dataModule,
                domainModule,
                appModule,
            )
        }
    }
}
