package com.mudassar.mykitten.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import com.mudassar.mykitten.util.inject
import com.mudassar.mykitten.kittens.onResult
import com.mudassar.mykitten.logger.ErrorLogger

@Composable
internal fun AsyncImage(
    url: String,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val imageLoader by remember { inject<ImageLoader>() }
    val errorLogger by remember { inject<ErrorLogger>() }
    var image by remember { mutableStateOf<ImageBitmap?>(null) }
    when (image) {
        null -> Image(
            painter = ColorPainter(Color.DarkGray),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale,
        )

        else -> Image(
            bitmap = image!!,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale,
        )
    }
    LaunchedEffect(url) {
        runCatching { imageLoader.loadImage(url) }
            .onResult {
                this?.let { image = it }
                it?.let(errorLogger::log)
            }
    }
}

expect fun ByteArray.toBitmap(): ImageBitmap
