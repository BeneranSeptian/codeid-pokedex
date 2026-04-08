package dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto

import dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.common.AbilitySlotResponse
import dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.common.StatResponse
import dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.common.TypeSlotResponse
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.PokemonDetail
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val stats: List<StatResponse>,
    val abilities: List<AbilitySlotResponse>,
    val types: List<TypeSlotResponse>
) {
    fun mapToEntity() = PokemonDetail(
        id = id,
        name = name,
        height = height,
        weight = weight,
        stats = stats.map { it.mapToEntity() },
        abilities = abilities.map { it.mapToEntity() },
        types = types.map { it.mapToEntity() }
    )
}
