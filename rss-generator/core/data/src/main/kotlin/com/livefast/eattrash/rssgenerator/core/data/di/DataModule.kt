package com.livefast.eattrash.rssgenerator.core.data.di

import com.livefast.eattrash.rssgenerator.core.data.PostRepository
import com.livefast.eattrash.rssgenerator.core.data.PostRepositoryImpl
import com.livefast.eattrash.rssgenerator.core.data.converter.PostDtoToModelConverter
import com.livefast.eattrash.rssgenerator.core.data.converter.PostDtoToModelConverterImpl
import org.koin.dsl.module

/**
 * DI module for the data layer.
 */
val dataModule =
    module {
        single<PostRepository> {
            PostRepositoryImpl(converter = get())
        }
        single<PostDtoToModelConverter> {
            PostDtoToModelConverterImpl(dateUtils = get())
        }
    }
