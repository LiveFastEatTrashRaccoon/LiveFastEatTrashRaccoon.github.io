package com.livefast.eattrash.rssgenerator

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph

@DependencyGraph(scope = AppScope::class)
interface RootGraph {
    val app: App
}
