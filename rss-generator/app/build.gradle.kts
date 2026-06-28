plugins {
    id("com.livefast.eattrash.jvm")
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

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation(libs.junit.jupiter.engine)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass = "com.livefast.eattrash.rssgenerator.MainKt"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
