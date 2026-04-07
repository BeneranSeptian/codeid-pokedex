package dev.septianbeneran.technicaltest.api.data.remote.service

import dev.septianbeneran.technicaltest.api.data.remote.dto.ItemResponse
import dev.septianbeneran.technicaltest.api.data.remote.dto.JsonBinDto
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult

interface JsonBinApiRemoteDataSource {
    suspend fun getItemList(
        bindId: String
    ): ApiResult<JsonBinDto<List<ItemResponse>>>
}