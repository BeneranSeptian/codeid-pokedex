package dev.septianbeneran.technicaltest.api.usecase

import dev.septianbeneran.technicaltest.api.repository.JsonBinRepository
import dev.septianbeneran.technicaltest.core.entity.model.Item
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemListUseCase @Inject constructor(
    private val repository: JsonBinRepository
) {
    operator fun invoke(bindId: String): Flow<ApiResult<List<Item>>> = repository.getItemList(bindId)
}
