package dev.septianbeneran.technicaltest.api.auth.usecase

import dev.septianbeneran.technicaltest.api.auth.repository.JsonBinRepository
import dev.septianbeneran.technicaltest.core.entity.model.Item
import javax.inject.Inject

class SaveItemListUseCase @Inject constructor(
    private val repository: JsonBinRepository
) {
    operator fun invoke(itemList: List<Item>) = repository.cacheDataSource.saveItemList(itemList)
}