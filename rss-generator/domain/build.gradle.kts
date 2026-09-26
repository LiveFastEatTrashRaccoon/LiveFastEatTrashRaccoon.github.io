plugins {
    id("com.livefast.eattrash.jvm")
    id("com.livefast.eattrash.di")
    id("com.livefast.eattrash.test")
    id("com.livefast.eattrash.spotless")
}

dependencies {
    implementation(libs.kotlin.xml.builder)

    implementation(project(":core:model"))
    implementation(project(":core:utils"))
}
