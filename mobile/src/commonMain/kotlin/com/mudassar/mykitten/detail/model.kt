package com.mudassar.mykitten.detail

import com.mudassar.mykitten.kittens.Data.KittenUiItem.BreadInfo

data class KittenDetailItem(
    val id: String,
    val url: String,
    val breadInfo: List<BreadInfo>,
)
