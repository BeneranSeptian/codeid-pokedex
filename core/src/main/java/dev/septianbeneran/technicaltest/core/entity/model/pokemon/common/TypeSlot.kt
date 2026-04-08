package dev.septianbeneran.technicaltest.core.entity.model.pokemon.common

import kotlinx.serialization.Serializable

@Serializable
data class TypeSlot(
    val slot: Int,
    val type: NamedResource
)
