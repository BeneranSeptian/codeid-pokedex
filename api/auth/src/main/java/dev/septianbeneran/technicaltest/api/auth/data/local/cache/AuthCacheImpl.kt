package dev.septianbeneran.technicaltest.api.auth.data.local.cache

import dev.septianbeneran.technicaltest.core.base.BaseCouchbase
import dev.septianbeneran.technicaltest.core.entity.model.User
import dev.septianbeneran.technicaltest.core.util.HashUtil.hashString
import javax.inject.Inject

class AuthCacheImpl @Inject constructor(
    private val baseCouchbase: BaseCouchbase
) : AuthCache {

    companion object {
        private const val COLLECTION = "users"
    }

    override fun registerUser(
        user: User
    ): Boolean {

        val existingUser = baseCouchbase.getObject<User>(
                collectionName = COLLECTION,
                documentId = user.email
            )

        if (existingUser != null) {
            return false
        }

        baseCouchbase.saveObject(
            collectionName = COLLECTION,
            documentId = user.email,
            value = user.copy(password = hashString(user.password))
        )

        return true
    }

    override fun findUserByEmail(
        email: String
    ) = baseCouchbase.getObject<User>(
        collectionName = COLLECTION,
        documentId = email
    )

    override fun loginUser(
        email: String,
        password: String
    ): Boolean {

        val user = baseCouchbase.getObject<User>(
                collectionName = COLLECTION,
                documentId = email
            )

        return user?.password == hashString(password)
    }
}