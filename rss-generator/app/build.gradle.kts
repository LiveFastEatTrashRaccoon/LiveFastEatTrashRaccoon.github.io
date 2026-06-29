plugins {
    id("com.livefast.eattrash.jvm")
    id("com.livefast.eattrash.test")
    id("com.livefast.eattrash.spotless")
    application
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    implementation(project(":core:model"))
    implementation(project(":core:utils"))
    implementation(project(":core:data"))
    implementation(project(":domain"))
}

application {
    mainClass = "com.livefast.eattrash.rssgenerator.MainKt"
}
