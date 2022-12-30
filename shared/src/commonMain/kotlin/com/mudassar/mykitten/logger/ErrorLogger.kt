package com.mudassar.mykitten.logger

import com.mudassar.mykitten.model.AppConfig
import com.mudassar.mykitten.model.isQa

interface ErrorLogger {
    fun log(error: Throwable)
}

class DefaultErrorLogger(private val config: AppConfig) : ErrorLogger {
    override fun log(error: Throwable) {
        if (config.environment.isQa()) {
            error.printStackTrace()
        }
    }
}
