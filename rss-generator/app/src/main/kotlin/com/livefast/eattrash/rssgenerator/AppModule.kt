package com.livefast.eattrash.rssgenerator

import org.koin.dsl.module

/**
 * DI module for the application.
 */
val appModule = module {
    single<App> { AppImpl(repository = get(), generator = get(), printer = get()) }
}
