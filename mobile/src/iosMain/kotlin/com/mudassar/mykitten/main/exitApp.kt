@file:Suppress("FunctionName")

package com.mudassar.mykitten.main

import com.mudassar.mykitten.model.Platform

actual fun SuspendApp(platform: Platform) {
    platform.onSuspendApp.invoke()
}
