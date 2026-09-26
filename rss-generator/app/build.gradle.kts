plugins {
    id("com.livefast.eattrash.jvm")
    id("com.livefast.eattrash.di")
    id("com.livefast.eattrash.test")
    id("com.livefast.eattrash.spotless")
    application
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:utils"))
    implementation(project(":core:data"))
    implementation(project(":domain"))
}

application {
    mainClass = "com.livefast.eattrash.rssgenerator.MainKt"
}
