package com.mudassar.mykitten.analytics

import android.content.Context

actual class EventLogger(private val context: Context) {

    init {
        //initialize firebase
    }

    actual fun logEvent(event: Event) {
        println(
            """
                log Firebase android event
                event name=${event.name}
                attributes = ${event.attributes}
            """.trimIndent()
        )
    }
}
