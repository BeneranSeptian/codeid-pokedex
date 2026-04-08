package dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.common

import dev.septianbeneran.technicaltest.core.entity.model.pokemon.common.AbilitySlot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AbilitySlotResponse(
    val ability: NamedResourceResponse,
    @SerialName("is_hidden")val isHidden: Boolean,
    val slot: Int
){
    fun mapToEntity() = AbilitySlot(
        ability = ability.mapToEntity(),
        isHidden = isHidden,
        slot = slot
    )
}