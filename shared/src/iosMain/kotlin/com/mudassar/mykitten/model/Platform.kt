package com.mudassar.mykitten.model

import com.mudassar.mykitten.analytics.EventLogger

actual class Platform(
    val onSuspendApp: () -> Unit,
    actual val apiKey: String
) {
    actual val eventLogger
        get() = EventLogger()
}
