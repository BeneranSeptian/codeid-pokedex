package dev.septianbeneran.technicaltest.api.usecase

import dev.septianbeneran.technicaltest.api.repository.JsonBinRepository
import dev.septianbeneran.technicaltest.core.entity.local.LocalResult
import dev.septianbeneran.technicaltest.core.entity.model.Item
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadItemListUseCase @Inject constructor(
    private val repository: JsonBinRepository
) {
    operator fun invoke(): Flow<LocalResult<List<Item>>> = repository.cacheDataSource.loadItemList()
}