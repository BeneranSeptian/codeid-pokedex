package dev.septianbeneran.technicaltest.api.auth.repository

import dev.septianbeneran.technicaltest.api.auth.data.local.cache.AuthCache
import dev.septianbeneran.technicaltest.api.auth.data.local.cache.SessionCache
import dev.septianbeneran.technicaltest.core.base.BaseRepository
import dev.septianbeneran.technicaltest.core.entity.model.auth.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    override val authCache: AuthCache,
    override val sessionCache: SessionCache
) : AuthRepository, BaseRepository() {

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

