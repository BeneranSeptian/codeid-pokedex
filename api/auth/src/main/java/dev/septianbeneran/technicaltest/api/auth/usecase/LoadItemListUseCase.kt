package dev.septianbeneran.technicaltest.api.auth.usecase

import dev.septianbeneran.technicaltest.api.auth.repository.AuthRepository
import dev.septianbeneran.technicaltest.core.entity.local.LocalResult
import dev.septianbeneran.technicaltest.core.entity.model.Item
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadItemListUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<LocalResult<List<Item>>> = repository.cacheDataSource.loadItemList()
}