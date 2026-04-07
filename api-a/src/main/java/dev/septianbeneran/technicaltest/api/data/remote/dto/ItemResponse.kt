package dev.septianbeneran.technicaltest.api.data.remote.dto

import dev.septianbeneran.technicaltest.core.entity.model.Item
import kotlinx.serialization.Serializable

@Serializable
data class ItemResponse(val id: Int, val name: String, val description: String) {
    fun mapToEntity() = Item(id, name, description)
}
