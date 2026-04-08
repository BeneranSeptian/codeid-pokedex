package dev.septianbeneran.technicaltest.core.entity.model.pokemon.common

import kotlinx.serialization.Serializable

@Serializable
data class NamedResource(
    val name: String,
    val url: String
)