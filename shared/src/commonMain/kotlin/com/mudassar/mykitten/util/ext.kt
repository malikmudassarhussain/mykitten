package com.mudassar.mykitten.util

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

inline fun <reified T> inject() = lazy {
    object : KoinComponent {
        val result: T by inject()
    }.result
}
