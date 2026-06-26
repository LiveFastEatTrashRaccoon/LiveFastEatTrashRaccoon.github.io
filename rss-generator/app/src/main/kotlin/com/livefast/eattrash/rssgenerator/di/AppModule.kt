package com.livefast.eattrash.rssgenerator.di

import com.livefast.eattrash.rssgenerator.App
import com.livefast.eattrash.rssgenerator.AppImpl
import org.koin.dsl.module

/**
 * DI module for the application.
 */
val appModule = module {
    single<App> { AppImpl(repository = get(), generator = get(), printer = get()) }
}
