plugins {
    id("com.livefast.eattrash.jvm")
    id("com.livefast.eattrash.spotless")
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.kotlin.xml.builder)

    implementation(project(":core:model"))
    implementation(project(":core:utils"))
}
