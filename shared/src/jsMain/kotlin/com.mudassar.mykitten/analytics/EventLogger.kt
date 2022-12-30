package com.mudassar.mykitten.analytics

actual class EventLogger {
    actual fun logEvent(event: Event) {
        println(
            """
                log Firebase JS event
                event name=${event.name}
                attributes = ${event.attributes}
            """.trimIndent()
        )
    }
}
