package dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.common

import dev.septianbeneran.technicaltest.core.entity.model.pokemon.common.TypeSlot
import kotlinx.serialization.Serializable

@Serializable
data class TypeSlotResponse(
    val slot: Int,
    val type: NamedResourceResponse
) {
    fun mapToEntity() = TypeSlot(
        slot = slot,
        type = type.mapToEntity()
    )
}
