package dev.septianbeneran.technicaltest.core.util


import android.util.Log
import dev.septianbeneran.technicaltest.core.entity.local.LocalResult
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber

fun <A> resultFlow(
    networkCall: suspend () -> ApiResult<A>,
    dispatcher: CoroutineDispatcherProvider
): Flow<ApiResult<A>> = flow {
    emit(ApiResult.Loading())
    when (val response = networkCall()) {
        is ApiResult.Success -> emit(ApiResult.Success(response.data, response.headers))
        is ApiResult.Loading -> emit(ApiResult.Loading())
        is ApiResult.Error -> emit(ApiResult.Error(response.error))
    }
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

