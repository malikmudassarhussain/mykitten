package com.mudassar.mykitten.model

import com.mudassar.mykitten.analytics.EventLogger

actual class Platform {
    actual val eventLogger
        get() = EventLogger()
    actual val apiKey
        get() = ""
}
