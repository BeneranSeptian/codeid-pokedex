package dev.septianbeneran.technicaltest.core.entity.local

sealed class LocalResult<out T> {
    data class Success<out T>(val data: T?) : LocalResult<T>()
    data class Error(val errorMessage: String) : LocalResult<Nothing>()
    class Empty: LocalResult<Nothing>()
}