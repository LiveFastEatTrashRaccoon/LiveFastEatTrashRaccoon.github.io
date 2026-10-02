package plugins

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import utils.getPluginId
import utils.getVersion
import utils.libs

/**
 * Convention plugin to apply common Spotless and Ktlint configuration.
 */
class SpotlessPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit =
        with(target) {
            with(pluginManager) {
                apply(libs.getPluginId("spotless"))
            }
            extensions.configure(SpotlessExtension::class.java) {
                kotlin {
                    target("**/*.kt")
                    targetExclude("**/build/**/*.kt")
                    ktlint(libs.getVersion("ktlint").toString())
                    trimTrailingWhitespace()
                    endWithNewline()
                }
                kotlinGradle {
                    target("*.gradle.kts")
                    ktlint(libs.getVersion("ktlint").toString())
                }
            }
        }
}
