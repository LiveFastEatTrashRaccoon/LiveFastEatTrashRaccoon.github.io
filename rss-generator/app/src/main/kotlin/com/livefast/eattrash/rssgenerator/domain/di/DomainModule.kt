package com.livefast.eattrash.rssgenerator.domain.di

import com.livefast.eattrash.rssgenerator.domain.RssGenerator
import com.livefast.eattrash.rssgenerator.domain.RssGeneratorImpl
import org.koin.dsl.module

val domainModule = module {
    single<RssGenerator> {
        RssGeneratorImpl(dateUtils = get())
    }
}
