package com.mudassar.mykitten.model

import com.mudassar.mykitten.analytics.EventLogger

expect class Platform {
    val eventLogger: EventLogger
    val apiKey: String
}
