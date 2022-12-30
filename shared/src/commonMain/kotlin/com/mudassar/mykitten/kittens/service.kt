package com.mudassar.mykitten.kittens

import com.mudassar.mykitten.http.HttpClients
import com.mudassar.mykitten.http.cacheResponse
import com.mudassar.mykitten.logger.ErrorLogger
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class KittensService(
    private val kittensApi: KittensApi,
    private val errorLogger: ErrorLogger
) {
    suspend fun fetchKittens(forceRefresh: Boolean) =
        withContext(Dispatchers.Default) {
            val items = kittensApi.kittens(forceRefresh)
                .map { kitten ->
                    async {
                        runCatching {
                            kittensApi.kittenDetail(
                                id = kitten.id,
                                forceRefresh = forceRefresh
                            )
                        }
                            .onFailure(errorLogger::log)
                            .getOrElse { errorLogger.log(it);null }
                    }
                }.awaitAll()
                .filterNotNull()
            KittenDto(items)
        }
}

interface KittensApi {
    suspend fun kittens(forceRefresh: Boolean): List<Kitten>
    suspend fun kittenDetail(id: String, forceRefresh: Boolean): KittenDetail
}

class KittensApiImpl(private val httpClients: HttpClients) : KittensApi {

    private val httpClient by lazy { httpClients.authenticatedClient }

    override suspend fun kittens(forceRefresh: Boolean): List<Kitten> =
        httpClient.get("v1/images/search") {
            parameter("limit", ResultLimit)
            cacheResponse(forceRefresh)
        }.body()

    override suspend fun kittenDetail(id: String, forceRefresh: Boolean): KittenDetail =
        httpClient.get("v1/images/$id") {
            cacheResponse(forceRefresh)
        }.body()

}

private const val ResultLimit = 20
