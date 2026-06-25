package com.livefast.eattrash.rssgenerator.core.di

import com.livefast.eattrash.rssgenerator.core.DateUtils
import com.livefast.eattrash.rssgenerator.core.DateUtilsImpl
import org.koin.dsl.module

/**
 * DI module for core utilities.
 */
val coreModule = module {
    single<DateUtils> {
        DateUtilsImpl()
    }
}
