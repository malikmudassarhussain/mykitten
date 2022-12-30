@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.jb.compose) apply false
    alias(libs.plugins.jb.serialization) apply false
    alias(libs.plugins.ksp) apply false
    id("com.github.ben-manes.versions") version "0.44.0"
}

//tasks.register("clean", Delete::class) {
//    delete(rootProject.buildDir)
//}
