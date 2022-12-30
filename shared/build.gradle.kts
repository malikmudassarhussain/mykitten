@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jb.serialization)
    kotlin("native.cocoapods")
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    js {
        moduleName = "js-shared"
        browser {
            webpackTask {
                output.library = "jsShared"
            }
            binaries.library()
        }
    }

    cocoapods {
        summary = "Common Module for business logic"
        homepage = "Common Module"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlin.stdlib.jdk8)
                implementation(libs.kotlin.coroutines.core)

                implementation(libs.jb.atomicfu)

                api(libs.ktor.client.core)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.json)
                implementation(libs.ktor.client.serialization)
                implementation(libs.ktor.client.contentNegotiation)
                implementation(libs.ktor.serialization.json)

                implementation(libs.koin.core)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
        val jsMain by getting
    }
}

android {
    namespace = "com.mudassar.mykitten"
    compileSdk = 33
    defaultConfig {
        minSdk = 24

        val apiKey = gradleLocalProperties(rootDir).run {
            if (containsKey("api.key")) getProperty("api.key") else "\"\""
        }
        buildConfigField("String", "apiKey", apiKey)

        buildFeatures {
            buildConfig = true
        }
    }
}
