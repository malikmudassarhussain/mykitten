package com.mudassar.mykitten.analytics

expect class EventLogger {
    fun logEvent(event: Event)
}

class Event(
    val name: String,
    val attributes: Map<String, Any> = emptyMap()
)
