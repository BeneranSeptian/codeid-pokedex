package dev.septianbeneran.technicaltest.core.entity.model

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: Int,
    val name: String,
    val description: String
)