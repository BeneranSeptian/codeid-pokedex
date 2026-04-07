package dev.septianbeneran.technicaltest.api.data.local.cache

import com.couchbase.lite.DataSource
import com.couchbase.lite.Expression
import com.couchbase.lite.MutableDocument
import com.couchbase.lite.QueryBuilder
import com.couchbase.lite.SelectResult
import dev.septianbeneran.technicaltest.core.base.BaseCouchbase
import dev.septianbeneran.technicaltest.core.di.CouchbaseManager
import dev.septianbeneran.technicaltest.core.entity.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
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
            value = user
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

        return user?.password == password
    }
//
//    /**
//     * Optional — Get all users
//     * Useful for debugging or admin screen
//     */
//    override fun getAllUsers(): Flow<List<User>> =
//        flow {
//
//            val users =
//                baseCouchbase.getAllObjects<User>(
//                    collectionName = COLLECTION
//                )
//
//            emit(users)
//        }
}