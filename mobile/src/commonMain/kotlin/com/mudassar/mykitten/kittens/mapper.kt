package com.mudassar.mykitten.kittens

import com.mudassar.mykitten.detail.KittenDetailItem
import com.mudassar.mykitten.kittens.Data.KittenUiItem.BreadInfo
import com.mudassar.mykitten.navigation.Navigator
import com.mudassar.mykitten.navigation.Route
import org.koin.core.annotation.Single

@Single
class KittenMapper {

    operator fun invoke(
        dto: KittenDto?,
        onRetry: () -> Unit,
        onNavigate: (KittenDetailItem) -> Unit,
    ): KittensUiState {
        if (dto == null) return ErrorState(onRetry)
        return Data(
            items = dto.items
                .filter { it.breeds.isNotEmpty() }
                .map { it.toUiItem() },
            onClickDetail = {
                onNavigate(
                    KittenDetailItem(
                        id = it.id,
                        url = it.url,
                        breadInfo = it.breadInfo
                    )
                )
            },
            onRefresh = onRetry,
        )
    }

    private fun KittenDetail.toUiItem(): Data.KittenUiItem {
        return Data.KittenUiItem(
            id = id,
            url = url,
            breadInfo = breeds.map { bread ->
                with(bread) {
                    BreadInfo(
                        id = id,
                        name = name,
                        origin = origin,
                        description = description,
                        temperament = temperament,
                        lifeSpan = life_span,
                        wikipediaUrl = wikipedia_url,
                        imperialWeight = weight.imperial,
                        metricWeight = weight.metric
                    )
                }
            }
        )
    }
}
