package dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.common

import dev.septianbeneran.technicaltest.core.entity.model.pokemon.common.NamedResource
import kotlinx.serialization.Serializable

@Serializable
data class NamedResourceResponse(
    val name: String,
    val url: String
){
    fun mapToEntity() = NamedResource(
        name = name,
        url = url
    )
}
