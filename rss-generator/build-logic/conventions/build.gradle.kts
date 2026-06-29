plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.spotless.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("jvmPlugin") {
            id = "com.livefast.eattrash.jvm"
            implementationClass = "plugins.JvmPlugin"
        }
        register("spotlessPlugin") {
            id = "com.livefast.eattrash.spotless"
            implementationClass = "plugins.SpotlessPlugin"
        }
        register("testPlugin") {
            id = "com.livefast.eattrash.test"
            implementationClass = "plugins.TestPlugin"
        }
    }
}
