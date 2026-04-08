package dev.septianbeneran.technicaltest.core.entity.model.pokemon.common

data class AbilitySlot(
    val ability: NamedResource,
    val isHidden: Boolean,
    val slot: Int
)