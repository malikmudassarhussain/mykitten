@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jb.compose)
}

kotlin {
    js(IR) {
        moduleName = "js-web"
        browser {
            webpackTask {
                outputFileName = "js-web.js"
            }
        }
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {

            dependencies {
                implementation(compose.runtime)
                implementation(compose.web.core)
            }
        }
    }

    tasks.named("jsBrowserDevelopmentRun")
        .configure { dependsOn("jsDevelopmentExecutableCompileSync") }

}

afterEvaluate {
    rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
        nodeVersion = "16.0.0"
        versions.webpackDevServer.version = "4.0.0"
        versions.webpackCli.version = "4.10.0"
    }
}
