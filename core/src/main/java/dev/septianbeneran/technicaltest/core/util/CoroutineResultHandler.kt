package dev.septianbeneran.technicaltest.core.util


import dev.septianbeneran.technicaltest.core.entity.local.LocalResult
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber

fun <Response, Entity> resultFlow(
    networkCall: suspend () -> ApiResult<Response>,
    dispatcher: CoroutineDispatcherProvider,
    saveToLocal: ((Entity) -> Unit)? = null,
    loadFromLocal: (() -> Entity?)? = null,
    transform: (Response?) -> Entity
): Flow<ApiResult<Entity?>> = flow {
    emit(ApiResult.Loading())

    val local = loadFromLocal?.invoke()
    if (local != null) {
        emit(ApiResult.Success(local))
        return@flow
    }

    when (val response = networkCall()) {
        is ApiResult.Success -> {
            val data = transform(response.data)
            saveToLocal?.invoke(data)
            emit(ApiResult.Success(data, response.headers))
        }
        is ApiResult.Loading -> emit(ApiResult.Loading())
        is ApiResult.Error -> emit(ApiResult.Error(response.error))
    }
}.flowOn(dispatcher.io())

fun <Entity> localFlow(
    localCall: () -> Entity?,
    dispatcher: CoroutineDispatcherProvider
): Flow<ApiResult<Entity>> = flow {
    emit(ApiResult.Loading())
    val data = localCall()
    emit(ApiResult.Success(data))
}.flowOn(dispatcher.io())

fun <T : Any> loadFlow(
    localCall: () -> Flow<T?>,
    dispatcher: CoroutineDispatcherProvider
): Flow<LocalResult<T>> =
    localCall()
        .map { data ->
            if (data != null) LocalResult.Success(data)
            else {
                Timber.tag("loadFlow").e("No cached data found")
                LocalResult.Empty()
            }
        }
        .catch { e ->
            Timber.tag("loadFlow").d("${e.message}")
            emit(LocalResult.Error(e.message ?: "Unknown Error"))
        }
        .flowOn(dispatcher.io())

fun saveFlow(
    localCall: suspend () -> Unit,
    dispatcher: CoroutineDispatcherProvider
): Flow<LocalResult<Unit>> = flow<LocalResult<Unit>> {
    localCall()
    emit(LocalResult.Success(Unit))
}.catch { e ->
    Timber.tag("saveFlow").d("${e.message}")
    emit(LocalResult.Error(e.message ?: "Unknown Error"))
}.flowOn(dispatcher.io())

