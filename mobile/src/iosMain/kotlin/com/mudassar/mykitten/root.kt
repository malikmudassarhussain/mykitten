package com.mudassar.mykitten

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Application
import com.mudassar.mykitten.main.app
import platform.CoreGraphics.CGFloat

private var padding by mutableStateOf(PaddingValues())

fun RootView() = Application {
    app(Modifier.padding(padding))
}

fun setPadding(value: Padding) {
    with(value) {
        padding = PaddingValues(
            start = start.dp,
            top = top.dp,
            end = end.dp,
            bottom = bottom.dp,
        )
    }
}

data class Padding(
    val start: CGFloat,
    val top: CGFloat,
    val end: CGFloat,
    val bottom: CGFloat
)
