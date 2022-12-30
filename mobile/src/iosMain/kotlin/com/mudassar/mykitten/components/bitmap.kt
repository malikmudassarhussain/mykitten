package com.mudassar.mykitten.components

import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

actual fun ByteArray.toBitmap() =
    Image.makeFromEncoded(this).toComposeImageBitmap()
