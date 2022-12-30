package com.mudassar.mykitten

import com.mudassar.mykitten.di.initDiGraph
import com.mudassar.mykitten.model.Platform
import com.mudassar.mykitten.model.AppConfig
import com.mudassar.mykitten.model.Environment

fun koinIos(onSuspendApp: () -> Unit, apiKey: String) {
    initDiGraph(
        config = AppConfig(
            appVersion = "0.0.1",
            environment = Environment.Qa,
        ),
        platform = Platform(onSuspendApp, apiKey)
    )
}
