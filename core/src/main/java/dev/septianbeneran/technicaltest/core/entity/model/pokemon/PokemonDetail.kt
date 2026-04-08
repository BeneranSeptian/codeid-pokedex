package dev.septianbeneran.technicaltest.core.entity.model.pokemon

import dev.septianbeneran.technicaltest.core.entity.model.pokemon.common.AbilitySlot
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.common.Stat
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.common.TypeSlot

data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val stats: List<Stat>,
    val abilities: List<AbilitySlot>,
    val types: List<TypeSlot>
)
