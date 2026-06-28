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
    }
}
