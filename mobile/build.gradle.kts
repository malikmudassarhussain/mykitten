@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jb.compose)
    alias(libs.plugins.jb.serialization)
    alias(libs.plugins.ksp)
    kotlin("native.cocoapods")
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Shared Module for mobile ui"
        homepage = "Shared Mobile Module"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "mobile"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":shared"))
                implementation(libs.kotlin.stdlib.jdk8)
                implementation(libs.kotlin.coroutines.core)

                api(libs.jb.compose.runtime)
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                api(compose.materialIconsExtended)
                implementation(libs.jb.atomicfu)

                api(libs.koin.core)
                api(libs.koin.annotations)
            }
        }
        val androidMain by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    namespace = "com.mudassar.mykitten.mobile"
    compileSdk = 33
    defaultConfig {
        minSdk = 24

        buildFeatures {
            buildConfig = true
        }
    }
}

dependencies {
    add("kspCommonMainMetadata",libs.koin.ksp.compiler)
    add("kspAndroid",libs.koin.ksp.compiler)
    add("kspIosX64",libs.koin.ksp.compiler)
    add("kspIosSimulatorArm64",libs.koin.ksp.compiler)
}

