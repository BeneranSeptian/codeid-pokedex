package dev.septianbeneran.technicaltest.api.data.local.cache

import dev.septianbeneran.technicaltest.core.entity.model.Item
import kotlinx.coroutines.flow.Flow

interface JsonBinCache {
    suspend fun saveItemList(itemList: List<Item>)
    fun loadItemList(): Flow<List<Item>?>
}