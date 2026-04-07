package dev.septianbeneran.technicaltest.api.repository

import dev.septianbeneran.technicaltest.api.data.local.cache.AuthCache
import dev.septianbeneran.technicaltest.api.data.local.service.JsonBinCacheDataSource
import dev.septianbeneran.technicaltest.core.entity.model.Item
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult
import kotlinx.coroutines.flow.Flow

interface JsonBinRepository {
    val cacheDataSource: JsonBinCacheDataSource
    val authCache: AuthCache
    fun getItemList(bindId: String): Flow<ApiResult<List<Item>>>
}
