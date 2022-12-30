@file:Suppress("UnstableApiUsage")

include(":mobile")


enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyKitten"
include(":androidApp")
include(":shared")
include(":web")
