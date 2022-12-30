package com.mudassar.mykitten.http

import com.mudassar.mykitten.model.Platform

class AppHeaders(private val platform: Platform) {
    fun headers() = buildMap {
        val key = platform.apiKey
        if (key.isNotEmpty()) put("x-api-key", key)
    }
}
