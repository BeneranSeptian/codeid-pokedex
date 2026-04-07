package dev.septianbeneran.technicaltest.core.base

import android.util.Log
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult
import dev.septianbeneran.technicaltest.core.entity.remote.ErrorResponse
import retrofit2.Response
import timber.log.Timber

abstract class BaseDataSource {
    suspend fun <T> getResult(
        call: suspend () -> Response<T>
    ): ApiResult<T> = try {
        val response = call()
        if (response.isSuccessful) ApiResult.Success(response.body(), response.headers())
        else {
            val errorContent = response.errorBody()?.string()

            Timber.tag("BaseDataSource").e("else: $errorContent")
            errorHandler(response.code(), errorContent.orEmpty())
        }
    } catch (e: Exception) {
        Timber.tag("BaseDataSource").e("catch: ${e.message}")
        errorHandler(6969, e.message ?: e.toString())
    }

    private fun <T> errorHandler(
        httpCode: Int,
        errorMessage: String
    ): ApiResult.Error<T> {
        return ApiResult.Error(
            error = ErrorResponse(
                code = httpCode,
                message = errorMessage
            )
        )
    }
}