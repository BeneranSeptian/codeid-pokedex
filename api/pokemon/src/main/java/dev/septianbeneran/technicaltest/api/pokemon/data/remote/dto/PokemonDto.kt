package dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDto<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: T
)
