plugins {
    id("com.livefast.eattrash.jvm")
    id("com.livefast.eattrash.test")
    id("com.livefast.eattrash.spotless")
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}
