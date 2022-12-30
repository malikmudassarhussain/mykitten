package com.mudassar.mykitten.analytics

actual class EventLogger {

    actual fun logEvent(event: Event) {
        println(
            """
                log Firebase ios event
                event name=${event.name}
                attributes = ${event.attributes}
            """.trimIndent()
        )
    }
}
