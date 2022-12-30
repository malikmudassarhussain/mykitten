package com.mudassar.mykitten.main

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.mudassar.mykitten.model.Platform

@Composable
actual fun SuspendApp(platform: Platform) {
    LocalContext.current.findActivity()?.finish()
}

private fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
