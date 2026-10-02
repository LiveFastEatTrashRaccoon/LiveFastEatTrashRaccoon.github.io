package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import utils.getLibrary
import utils.libs

class TestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                "testImplementation"(kotlin("test"))
                "testImplementation"(libs.getLibrary("kotlinx-coroutines-test"))
                "testImplementation"(libs.getLibrary("mockk"))
                "testImplementation"(libs.getLibrary("junit-jupiter-engine"))
                "testRuntimeOnly"(libs.getLibrary("junit-platform-launcher"))
            }

            tasks.withType(Test::class.java) {
                useJUnitPlatform()
            }
        }
    }
}
