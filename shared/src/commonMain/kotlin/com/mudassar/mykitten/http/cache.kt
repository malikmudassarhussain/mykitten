package com.mudassar.mykitten.http

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.http.CacheControl
import io.ktor.http.CacheControl.Visibility
import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpHeaders

internal const val ForcedHttpCache = "forceCache"
internal const val CustomResponseHeader = "x-custom-response"
internal const val CustomCacheControlResponseHeader = "x-custom-cache-control"
internal const val CacheTimeInSeconds = 600

fun HttpRequestBuilder.cacheResponse(forceRefresh: Boolean) {
    with(headers) {
        append(CustomResponseHeader, CustomCacheControlResponseHeader)
        if (forceRefresh) {
            append(
                HttpHeaders.CacheControl,
                CacheControl.NoCache(Visibility.Public).toString()
            )
        }
    }
}

fun addResponseHeadersIfRequired(response: HttpResponse) =
    response.request.headers[CustomResponseHeader]?.run {
        if (this == CustomCacheControlResponseHeader) {
            HeadersBuilder().apply {
                appendAll(response.headers)
                remove(HttpHeaders.CacheControl)
                append(
                    HttpHeaders.CacheControl,
                    CacheControl.MaxAge(CacheTimeInSeconds, visibility = Visibility.Public)
                        .toString()
                )
            }.build()
        } else null
    } ?: response.headers
