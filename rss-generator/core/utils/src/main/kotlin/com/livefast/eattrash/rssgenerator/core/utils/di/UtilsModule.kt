package com.livefast.eattrash.rssgenerator.core.utils.di

import com.livefast.eattrash.rssgenerator.core.utils.DateUtils
import com.livefast.eattrash.rssgenerator.core.utils.DateUtilsImpl
import org.koin.dsl.module

/**
 * DI module for core utilities.
 */
val utilsModule =
    module {
        single<DateUtils> {
            DateUtilsImpl()
        }
    }
