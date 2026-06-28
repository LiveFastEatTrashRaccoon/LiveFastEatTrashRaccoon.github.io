rootProject.name = "rssgenerator"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

includeBuild("build-logic")

include(":app")

include(":core:model")
include(":core:utils")
include(":core:data")

include(":domain")
