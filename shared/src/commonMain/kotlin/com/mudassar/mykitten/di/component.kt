package com.mudassar.mykitten.di

import com.mudassar.mykitten.http.AppHeaders
import com.mudassar.mykitten.http.CustomResponseHeader
import com.mudassar.mykitten.http.ForcedHttpCache
import com.mudassar.mykitten.http.HttpClients
import com.mudassar.mykitten.http.addResponseHeadersIfRequired
import com.mudassar.mykitten.kittens.KittensApi
import com.mudassar.mykitten.kittens.KittensApiImpl
import com.mudassar.mykitten.kittens.KittensService
import com.mudassar.mykitten.logger.DefaultErrorLogger
import com.mudassar.mykitten.logger.ErrorLogger
import com.mudassar.mykitten.model.AppConfig
import com.mudassar.mykitten.model.isQa
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.HttpReceivePipeline
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun configureKoin(configure: KoinApplication.() -> Unit = { }) = startKoin {
    //Todo: Use koin annotations once JS is supported
    configure()
    modules(
        module {
            factory { AppHeaders(get()) }
            single<HttpClients> { DefaultHttpClients(get(), get()) }
            factory { KittensService(get(), get()) }
            factory<KittensApi> { KittensApiImpl(get()) }
            factory<ErrorLogger> { DefaultErrorLogger(get()) }
        })
}

private class DefaultHttpClients(
    private val config: AppConfig,
    private val appHeaders: AppHeaders
) : HttpClients {

    override val simpleClient by lazy { baseClient() }

    override val authenticatedClient by lazy {
        baseClient(
            logLevel = LogLevel.ALL,
            authenticatedHttpClient()
        )
    }

    private fun baseClient(
        logLevel: LogLevel = LogLevel.INFO,
        block: HttpClientConfig<*>.() -> Unit = {}
    ) = HttpClient {
        install(HttpCache)
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        if (config.environment.isQa()) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HttpClient: $message")
                    }
                }
                level = logLevel
            }
        }

        block()
    }

    private fun authenticatedHttpClient(): HttpClientConfig<*>.() -> Unit = {
        expectSuccess = true
        defaultRequest {
            url(config.environment.baseUrl)
            appHeaders.headers().forEach { (k, v) ->
                this.headers.append(k, v)
            }
        }
        install(ForcedHttpCache) {
            receivePipeline.intercept(HttpReceivePipeline.Before) { response ->
                val newResponse =
                    if (!response.request.headers.contains(CustomResponseHeader)) response
                    else object : HttpResponse() {
                        override val call = response.call

                        @InternalAPI
                        override val content = response.content
                        override val coroutineContext = response.coroutineContext
                        override val headers = addResponseHeadersIfRequired(response)
                        override val requestTime = response.requestTime
                        override val responseTime = response.responseTime
                        override val status = response.status
                        override val version = response.version
                    }
                proceedWith(newResponse)
            }
        }
    }
}

