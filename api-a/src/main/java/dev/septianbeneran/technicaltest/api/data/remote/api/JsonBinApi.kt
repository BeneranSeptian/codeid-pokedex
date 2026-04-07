package dev.septianbeneran.technicaltest.api.data.remote.api

import dev.septianbeneran.technicaltest.api.data.remote.dto.ItemResponse
import dev.septianbeneran.technicaltest.api.data.remote.dto.JsonBinDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface JsonBinApi {
    @GET
    suspend fun getItemList(
        @Url url: String
    ): Response<JsonBinDto<List<ItemResponse>>>
}