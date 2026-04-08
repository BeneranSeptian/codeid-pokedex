package dev.septianbeneran.technicaltest.core.entity.model.pokemon.common

import kotlinx.serialization.Serializable

@Serializable
data class AbilitySlot(
    val ability: NamedResource,
    val isHidden: Boolean,
    val slot: Int
)