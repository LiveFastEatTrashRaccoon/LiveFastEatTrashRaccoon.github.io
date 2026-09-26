package com.livefast.eattrash.rssgenerator

import dev.zacsweers.metro.createGraph
import kotlinx.coroutines.runBlocking

/**
 * Application entry point.
 */
fun main() =
    runBlocking {
        val rootGraph = createGraph<RootGraph>()
        rootGraph.app.run()
    }
