package com.livefast.eattrash.rssgenerator.domain.di

import com.livefast.eattrash.rssgenerator.domain.FilePrinter
import com.livefast.eattrash.rssgenerator.domain.FilePrinterImpl
import com.livefast.eattrash.rssgenerator.domain.RssGenerator
import com.livefast.eattrash.rssgenerator.domain.RssGeneratorImpl
import org.koin.dsl.module

/**
 * DI module for the domain layer.
 */
val domainModule = module {
    single<RssGenerator> {
        RssGeneratorImpl(dateUtils = get())
    }
    single<FilePrinter> {
        FilePrinterImpl()
    }
}
