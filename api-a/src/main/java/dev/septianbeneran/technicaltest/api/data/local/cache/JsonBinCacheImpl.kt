package dev.septianbeneran.technicaltest.api.data.local.cache

import dev.septianbeneran.technicaltest.core.base.BaseDataStore
import dev.septianbeneran.technicaltest.core.entity.model.Item
import javax.inject.Inject

class JsonBinCacheImpl @Inject constructor(
    private val baseDataStore: BaseDataStore
) : JsonBinCache {
    override suspend fun saveItemList(itemList: List<Item>) =
        baseDataStore.saveObject("ITEM_LIST_KEY", itemList)

    override fun loadItemList() = baseDataStore.readObject<List<Item>>("ITEM_LIST_KEY")
}
