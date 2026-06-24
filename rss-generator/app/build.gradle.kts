plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.kotlin.xml.builder)

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation(libs.junit.jupiter.engine)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "com.livefast.eattrash.rssgenerator.MainKt"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
