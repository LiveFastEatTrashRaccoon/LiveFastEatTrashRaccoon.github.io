package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import utils.libs

class TestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                "testImplementation"(kotlin("test"))
                "testImplementation"(libs.findLibrary("kotlinx-coroutines-test").get())
                "testImplementation"(libs.findLibrary("mockk").get())
                "testImplementation"(libs.findLibrary("junit.jupiter.engine").get())
                "testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
            }

            tasks.withType(Test::class.java) {
                useJUnitPlatform()
            }
        }
    }
}
