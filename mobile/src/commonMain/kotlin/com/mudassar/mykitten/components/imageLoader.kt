package com.mudassar.mykitten.components

import androidx.compose.ui.graphics.ImageBitmap
import com.mudassar.mykitten.http.HttpClients
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

interface ImageLoader {
    suspend fun loadImage(url: String): ImageBitmap
}

@Single
class DefaultImageLoader(private val httpClients: HttpClients) : ImageLoader {
    private val memCache = LinkedHashMap<String, ImageBitmap>()
    private val httpClient by lazy { httpClients.simpleClient }

    override suspend fun loadImage(url: String): ImageBitmap {
        return memCache[url] ?: withContext(Dispatchers.Default) {
            httpClient.get(url).body<ByteArray>()
        }.toBitmap().also {
            memCache[url] = it
            if (memCache.size > MaxCacheSize) {
                //remove first item from memory
                memCache.remove(memCache.keys.first())
            }
        }
    }

    companion object {
        const val MaxCacheSize = 20
    }
}
