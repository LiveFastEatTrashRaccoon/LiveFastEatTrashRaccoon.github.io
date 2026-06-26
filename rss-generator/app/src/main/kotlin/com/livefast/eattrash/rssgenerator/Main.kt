package com.livefast.eattrash.rssgenerator

import com.livefast.eattrash.rssgenerator.di.DiHelper

/**
 * Application entry point.
 */
fun main() {
    DiHelper.setup()
    App.instance.run()
}
