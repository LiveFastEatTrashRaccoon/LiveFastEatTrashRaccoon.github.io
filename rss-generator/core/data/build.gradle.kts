plugins {
    id("com.livefast.eattrash.jvm")
    id("com.livefast.eattrash.test")
    id("com.livefast.eattrash.spotless")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.yamlkt)

    implementation(project(":core:model"))
    implementation(project(":core:utils"))
}
