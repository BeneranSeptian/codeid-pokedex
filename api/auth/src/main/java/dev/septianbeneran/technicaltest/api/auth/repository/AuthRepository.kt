package dev.septianbeneran.technicaltest.api.auth.repository

import dev.septianbeneran.technicaltest.api.auth.data.local.cache.AuthCache
import dev.septianbeneran.technicaltest.api.auth.data.local.cache.SessionCache
import dev.septianbeneran.technicaltest.core.entity.model.auth.User

interface AuthRepository {
    val authCache: AuthCache
    val sessionCache: SessionCache

    suspend fun loginUser(email: String, password: String): Boolean
    suspend fun registerUser(user: User): Boolean
}
