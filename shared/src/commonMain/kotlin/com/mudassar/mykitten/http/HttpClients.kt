package com.mudassar.mykitten.http

import io.ktor.client.HttpClient

interface HttpClients {
    val simpleClient: HttpClient
    val authenticatedClient: HttpClient
}
