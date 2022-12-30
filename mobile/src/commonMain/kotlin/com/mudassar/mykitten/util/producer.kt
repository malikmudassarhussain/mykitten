package com.mudassar.mykitten.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProduceStateScope
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Composable
internal fun <T> produceState(
    initialValue: T,
    onRefresh: suspend ProducerContext<T>.() -> Unit = {},
    producer: suspend ProduceStateScope<T>.(ProducerContext<T>) -> Unit
): State<T> {
    return produceState(initialValue) {
        val fetch = Channel<Unit>()
        with(ProducerContext(fetch, producer = this)) {
            launch { fetch.receiveAsFlow().collect { onRefresh() } }
            producer(this)
        }
    }
}

internal class ProducerContext<T>(
    private val onFetch: SendChannel<Unit>,
    producer: ProduceStateScope<T>
) : ProduceStateScope<T> by producer {

    fun refresh() {
        launch { onFetch.send(Unit) }
    }
}
