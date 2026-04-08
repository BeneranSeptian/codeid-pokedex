package dev.septianbeneran.technicaltest.api.auth.repository

import dev.septianbeneran.technicaltest.api.auth.data.local.cache.AuthCache
import dev.septianbeneran.technicaltest.api.auth.data.local.cache.SessionCache
import dev.septianbeneran.technicaltest.api.auth.data.local.service.JsonBinCacheDataSource
import dev.septianbeneran.technicaltest.core.entity.model.Item
import dev.septianbeneran.technicaltest.core.entity.model.User
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val cacheDataSource: JsonBinCacheDataSource
    val authCache: AuthCache
    val sessionCache: SessionCache
    fun getItemList(bindId: String): Flow<ApiResult<List<Item>>>

    suspend fun loginUser(email: String, password: String): Boolean
    suspend fun registerUser(user: User): Boolean
}
