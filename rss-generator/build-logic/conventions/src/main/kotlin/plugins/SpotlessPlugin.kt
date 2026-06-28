package plugins

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import utils.libs
import utils.pluginId
import utils.version

/**
 * Convention plugin to apply common Spotless and Ktlint configuration.
 */
class SpotlessPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit =
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("spotless").pluginId)
            }
            extensions.configure(SpotlessExtension::class.java) {
                kotlin {
                    target("**/*.kt")
                    targetExclude("**/build/**/*.kt")
                    ktlint(libs.findVersion("ktlint").version)
                    trimTrailingWhitespace()
                    endWithNewline()
                }
                kotlinGradle {
                    target("*.gradle.kts")
                    ktlint(libs.findVersion("ktlint").version)
                }
            }
        }
}
