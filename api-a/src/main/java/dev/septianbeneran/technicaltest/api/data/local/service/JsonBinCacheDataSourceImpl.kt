package dev.septianbeneran.technicaltest.api.data.local.service

import dev.septianbeneran.technicaltest.api.data.local.cache.JsonBinCache
import dev.septianbeneran.technicaltest.core.entity.local.LocalResult
import dev.septianbeneran.technicaltest.core.entity.model.Item
import dev.septianbeneran.technicaltest.core.util.CoroutineDispatcherProvider
import dev.septianbeneran.technicaltest.core.util.loadFlow
import dev.septianbeneran.technicaltest.core.util.saveFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JsonBinCacheDataSourceImpl @Inject constructor(
    private val jsonBinCache: JsonBinCache,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : JsonBinCacheDataSource {
    override fun saveItemList(itemList: List<Item>): Flow<LocalResult<Unit>> {
        return saveFlow(
            localCall = { jsonBinCache.saveItemList(itemList) },
            dispatcher = coroutineDispatcherProvider
        )
    }

    override fun loadItemList() = loadFlow(
        localCall = { jsonBinCache.loadItemList() },
        dispatcher = coroutineDispatcherProvider
    )
}