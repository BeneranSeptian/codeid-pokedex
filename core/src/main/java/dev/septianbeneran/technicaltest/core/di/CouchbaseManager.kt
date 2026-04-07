package dev.septianbeneran.technicaltest.core.di

import android.content.Context
import com.couchbase.lite.Collection
import com.couchbase.lite.CouchbaseLite
import com.couchbase.lite.Database
import com.couchbase.lite.DatabaseConfiguration
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CouchbaseManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private val database: Database

    init {
        CouchbaseLite.init(context)

        val config = DatabaseConfiguration()

        database = Database(
            "codeid-pokedex-db",
            config
        )
    }

    fun getCollection(name: String): Collection {

        return database.getCollection(name)
            ?: database.createCollection(name)
    }
}