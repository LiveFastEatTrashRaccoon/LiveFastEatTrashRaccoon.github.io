package com.livefast.eattrash.rssgenerator

import com.livefast.eattrash.rssgenerator.di.DiHelper
import kotlinx.coroutines.runBlocking

/**
 * Application entry point.
 */
fun main() =
    runBlocking {
        DiHelper.setup()
        App.instance.run()
    }
