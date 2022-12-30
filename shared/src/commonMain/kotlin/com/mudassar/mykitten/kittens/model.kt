package com.mudassar.mykitten.kittens

import kotlinx.serialization.Serializable

@Serializable
data class Kitten(val id: String)

@Serializable
data class KittenDetail(val id: String, val url: String, val breeds: List<BreedInfo> = emptyList())

@Serializable
data class BreedInfo(
    val id: String,
    val name: String,
    val origin: String,
    val description: String,
    val temperament: String,
    val life_span: String,
    val wikipedia_url: String,
    val weight: WeightInfo,
)

@Serializable
data class WeightInfo(val imperial: String, val metric: String)

data class KittenDto(val items: List<KittenDetail>)
