package dev.septianbeneran.technicaltest.api.auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class JsonBinDto<T> (
    val record: T,
    val metadata: JsonBinMetaData
) {
    @Serializable
    data class JsonBinMetaData(
        val id: String? = null,
        val private: Boolean? = null,
        val createdAt: String? = null,
        val name: String? = null
    )
}
