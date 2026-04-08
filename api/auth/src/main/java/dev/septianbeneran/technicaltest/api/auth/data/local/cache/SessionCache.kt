package dev.septianbeneran.technicaltest.api.auth.data.local.cache

import kotlinx.coroutines.flow.Flow

interface SessionCache {
    suspend fun saveLoginSession(email: String)
    fun loadLoggedInEmail(): Flow<String?>
    fun isLoggedIn(): Flow<Boolean>
    suspend fun logout()
}