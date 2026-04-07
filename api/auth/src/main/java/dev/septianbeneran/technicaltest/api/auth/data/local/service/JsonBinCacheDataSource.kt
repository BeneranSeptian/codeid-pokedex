package dev.septianbeneran.technicaltest.api.auth.data.local.service

import dev.septianbeneran.technicaltest.core.entity.local.LocalResult
import dev.septianbeneran.technicaltest.core.entity.model.Item
import kotlinx.coroutines.flow.Flow

interface JsonBinCacheDataSource {
    fun saveItemList(itemList: List<Item>): Flow<LocalResult<Unit>>
    fun loadItemList(): Flow<LocalResult<List<Item>>>
}