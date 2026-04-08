package dev.septianbeneran.technicaltest.core.entity.model.pokemon

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val name: String,
    val url: String
)
