package com.mudassar.mykitten.kittens

import androidx.compose.runtime.Composable
import com.mudassar.mykitten.analytics.EventLogger
import com.mudassar.mykitten.detail.KittenDetailItem
import com.mudassar.mykitten.logger.ErrorLogger
import com.mudassar.mykitten.navigation.Navigator
import com.mudassar.mykitten.navigation.Route
import com.mudassar.mykitten.util.ProducerContext
import com.mudassar.mykitten.util.produceState
import org.koin.core.annotation.Single

@Single
class KittensPresenter(
    private val service: KittensService,
    private val mapper: KittenMapper,
    private val navigator: Navigator,
    private val errorLogger: ErrorLogger,
    private val eventLogger: EventLogger,
) {
    @Composable
    internal fun presenter(): KittensUiState =
        produceState<KittensUiState>(
            initialValue = Loading,
            onRefresh = { fetch(forceRefresh = true) }
        ) {
            it.fetch()
        }.value

    private suspend fun ProducerContext<KittensUiState>.fetch(forceRefresh: Boolean = false) {
        runCatching { service.fetchKittens(forceRefresh) }
            .onResult {
                val onNavigate: (KittenDetailItem) -> Unit = {
                    eventLogger.logEvent(DetailEvent())
                    navigator.navigate(
                        Route(
                            id = "kitten-detail",
                            argument = it
                        )
                    )
                }
                val onRetry = {
                    value = Loading
                    refresh()
                }
                value = mapper(dto = this, onNavigate = onNavigate, onRetry = onRetry)
                it?.let(errorLogger::log)
            }
    }
}

sealed interface KittensUiState
object Loading : KittensUiState
data class ErrorState(val onRetry: () -> Unit) : KittensUiState
data class Data(
    val items: List<KittenUiItem>,
    val onClickDetail: (item: KittenUiItem) -> Unit,
    val onRefresh: () -> Unit
) : KittensUiState {
    data class KittenUiItem(
        val id: String,
        val url: String,
        val breadInfo: List<BreadInfo>,
    ) {
        data class BreadInfo(
            val id: String,
            val name: String,
            val origin: String,
            val description: String,
            val temperament: String,
            val lifeSpan: String,
            val wikipediaUrl: String,
            val imperialWeight: String,
            val metricWeight: String
        )
    }
}

inline fun <T> Result<T>.onResult(block: T?.(Throwable?) -> Unit) =
    onSuccess { it.run { block(null) } }
        .onFailure { null.block(it) }
