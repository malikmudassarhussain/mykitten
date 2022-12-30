package com.mudassar.mykitten.android

import android.app.Application
import com.mudassar.mykitten.di.initDiGraph
import com.mudassar.mykitten.model.Platform
import com.mudassar.mykitten.model.AppConfig
import com.mudassar.mykitten.model.Environment

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initDiGraph(appConfig(), Platform(applicationContext))

    }

    private fun appConfig() = AppConfig(
        appVersion = "0.0.1",
        environment = Environment.Qa,
    )
}
