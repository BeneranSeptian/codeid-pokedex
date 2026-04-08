package dev.septianbeneran.technicaltest.core.entity.model.pokemon.common

import kotlinx.serialization.Serializable

@Serializable
data class Stat(
    val baseStat: Int,
    val effort: Int,
    val stat: NamedResource
)