package dev.septianbeneran.technicaltest.api.auth.repository

import dev.septianbeneran.technicaltest.api.auth.data.local.cache.AuthCache
import dev.septianbeneran.technicaltest.api.auth.data.local.cache.SessionCache
import dev.septianbeneran.technicaltest.api.auth.data.local.service.JsonBinCacheDataSource
import dev.septianbeneran.technicaltest.api.auth.data.remote.service.JsonBinApiRemoteDataSource
import dev.septianbeneran.technicaltest.core.base.BaseRepository
import dev.septianbeneran.technicaltest.core.entity.model.User
import dev.septianbeneran.technicaltest.core.util.CoroutineDispatcherProvider
import dev.septianbeneran.technicaltest.core.util.resultFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remote: JsonBinApiRemoteDataSource,
    private val dispatcher: CoroutineDispatcherProvider,
    override val cacheDataSource: JsonBinCacheDataSource,
    override val authCache: AuthCache,
    override val sessionCache: SessionCache
) : AuthRepository, BaseRepository() {
    override fun getItemList(bindId: String) = resultFlow(
        networkCall = { remote.getItemList(bindId) },
        dispatcher = dispatcher
    ).mapToEntity {
        it?.record?.map { item -> item.mapToEntity() }
    }

    override suspend fun loginUser(email: String, password: String): Boolean {
        val isLoginSuccess = authCache.loginUser(email, password)
        if (isLoginSuccess) { sessionCache.saveLoginSession(email) }
        return isLoginSuccess
    }

    override suspend fun registerUser(
        user: User
    ): Boolean {
        val isRegisterSuccess = authCache.registerUser(user)
        if (isRegisterSuccess) { sessionCache.saveLoginSession(user.email) }
        return isRegisterSuccess
    }
}

