package dev.septianbeneran.technicaltest.core.base

import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult
import dev.septianbeneran.technicaltest.core.entity.remote.ErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class BaseRepository {
    private fun <ResponsePlain, Entity> ApiResult<ResponsePlain>.transformResult(
        saveToLocal: ((Entity) -> Unit)? = null,
        transform: (ResponsePlain?) -> Entity
    ) = try {
        when(this) {
            is ApiResult.Error -> ApiResult.Error(error)
            is ApiResult.Loading -> ApiResult.Loading()
            is ApiResult.Success -> {
                val entity = transform.invoke(data)
                saveToLocal?.invoke(entity)
                ApiResult.Success(entity)
            }
        }
    } catch (t: Throwable) {
        ApiResult.Error(ErrorResponse(6969, t.message))
    }

    protected fun <ResponsePlain, Entity> Flow<ApiResult<ResponsePlain>>.mapToEntity(
        saveToLocal: ((Entity) -> Unit)? = null,
        transform: (ResponsePlain?) -> Entity
    ) = this.map {
        it.transformResult(transform = transform, saveToLocal = saveToLocal)
    }
}