package dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto

import dev.septianbeneran.technicaltest.core.entity.model.pokemon.Pokemon
import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse(
    val name: String,
    val url: String
){
    fun mapToEntity() = Pokemon(
        name = name,
        url = url
    )
}
