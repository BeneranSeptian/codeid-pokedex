package dev.septianbeneran.technicaltest.api.auth.data.local.cache

import dev.septianbeneran.technicaltest.api.auth.util.SessionKeys.IS_LOGGED_IN
import dev.septianbeneran.technicaltest.api.auth.util.SessionKeys.USER_EMAIL
import dev.septianbeneran.technicaltest.core.base.BaseDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionCacheImpl @Inject constructor(
    private val baseDataStore: BaseDataStore
): SessionCache {

    override suspend fun saveLoginSession(email: String) {
        baseDataStore.saveString(USER_EMAIL, email)
        baseDataStore.saveBoolean(IS_LOGGED_IN, true)
    }

    override fun loadLoggedInEmail(): Flow<String?> {
        return baseDataStore.readString(USER_EMAIL)
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return baseDataStore
            .readBoolean(IS_LOGGED_IN)
            .map { it ?: false }
    }

    override suspend fun logout() {
        baseDataStore.saveBoolean(IS_LOGGED_IN, false)
        baseDataStore.saveString(USER_EMAIL, "")
    }
}