package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import utils.libs
import utils.pluginId

/**
 * Convention plugin to apply common JVM configuration.
 */
class JvmPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kotlin-jvm").pluginId)
            }
            dependencies {
                "implementation"(libs.findLibrary("kotlinx-coroutines-core").get())
            }
            extensions.configure(JavaPluginExtension::class.java) {
                toolchain.languageVersion.set(JavaLanguageVersion.of(21))
            }
            extensions.configure(KotlinProjectExtension::class.java) {
                jvmToolchain(21)
            }
        }
    }
}