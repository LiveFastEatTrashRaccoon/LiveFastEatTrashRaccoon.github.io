package com.livefast.eattrash.rssgenerator.di

import com.livefast.eattrash.rssgenerator.core.data.di.dataModule
import com.livefast.eattrash.rssgenerator.core.utils.di.utilsModule
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
                utilsModule,
                dataModule,
                domainModule,
                appModule,
            )
        }
    }
}
