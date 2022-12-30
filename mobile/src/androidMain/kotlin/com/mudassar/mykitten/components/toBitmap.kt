package com.mudassar.mykitten.components

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap

actual fun ByteArray.toBitmap() =
    BitmapFactory.decodeByteArray(this, 0, this.size).asImageBitmap()

