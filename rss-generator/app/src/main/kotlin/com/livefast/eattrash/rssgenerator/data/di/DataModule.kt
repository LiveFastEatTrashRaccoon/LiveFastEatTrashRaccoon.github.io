package com.livefast.eattrash.rssgenerator.data.di

import com.livefast.eattrash.rssgenerator.data.PostRepository
import com.livefast.eattrash.rssgenerator.data.PostRepositoryImpl
import org.koin.dsl.module

/**
 * DI module for the data layer.
 */
val dataModule = module {
    single<PostRepository> {
        PostRepositoryImpl(dateUtils = get())
    }
}
