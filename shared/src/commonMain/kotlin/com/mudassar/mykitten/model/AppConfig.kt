package com.mudassar.mykitten.model

class AppConfig(
    val appVersion: String,
    val environment: Environment,
)

enum class Environment(val baseUrl: String) {
    Prod("https://api.thecatapi.com/"),
    Qa("https://api.thecatapi.com/")
}

internal fun Environment.isQa() = this == Environment.Qa
