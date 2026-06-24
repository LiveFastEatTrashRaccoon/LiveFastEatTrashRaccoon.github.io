package com.livefast.eattrash.rssgenerator.core.di

import com.livefast.eattrash.rssgenerator.core.DateUtils
import com.livefast.eattrash.rssgenerator.core.DateUtilsImpl
import org.koin.dsl.module

val coreModule = module {
    single<DateUtils> {
        DateUtilsImpl()
    }
}
