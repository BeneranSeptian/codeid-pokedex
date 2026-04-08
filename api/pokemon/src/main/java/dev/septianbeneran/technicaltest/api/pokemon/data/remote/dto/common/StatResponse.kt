package dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.common

import dev.septianbeneran.technicaltest.core.entity.model.pokemon.common.NamedResource
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.common.Stat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatResponse(
    @SerialName("base_stat")val baseStat: Int,
    val effort: Int,
    val stat: NamedResourceResponse
) {
    fun mapToEntity() = Stat(
        baseStat = baseStat,
        effort = effort,
        stat = stat.mapToEntity()
    )
}
