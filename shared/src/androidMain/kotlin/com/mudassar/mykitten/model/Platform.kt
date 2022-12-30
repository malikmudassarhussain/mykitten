package com.mudassar.mykitten.model

import android.content.Context
import com.mudassar.mykitten.BuildConfig
import com.mudassar.mykitten.analytics.EventLogger

actual class Platform(val context: Context) {
    actual val eventLogger
        get() = EventLogger(context)
    actual val apiKey
        get() = BuildConfig.apiKey
}
