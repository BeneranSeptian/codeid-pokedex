package dev.septianbeneran.technicaltest.core.base

import com.couchbase.lite.DataSource
import com.couchbase.lite.QueryBuilder
import com.couchbase.lite.SelectResult
import dev.septianbeneran.technicaltest.core.di.CouchbaseManager
import dev.septianbeneran.technicaltest.core.util.toDocument
import dev.septianbeneran.technicaltest.core.util.toObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseCouchbase @Inject constructor(
    val couchbaseManager: CouchbaseManager
) {

    inline fun <reified T : Any> saveObject(
        collectionName: String,
        documentId: String,
        value: T
    ) {

        val collection = couchbaseManager.getCollection(collectionName)

        val doc =
            value.toDocument(documentId)

        collection.save(doc)

    }

    inline fun <reified T : Any> getObject(
        collectionName: String,
        documentId: String
    ): T? {

        val collection = couchbaseManager.getCollection(collectionName)
        val doc = collection.getDocument(documentId)

        return doc?.toObject<T>()

    }

    inline fun <reified T : Any> getAllObjects(
        collectionName: String
    ): List<T> {

        val collection = couchbaseManager.getCollection(collectionName)

        val query =
            QueryBuilder
                .select(SelectResult.all())
                .from(
                    DataSource.collection(
                        collection
                    )
                )

        val result = query.execute()
        val list = mutableListOf<T>()

        for (row in result) {
            val dict = row.getDictionary(collection.name)
            val obj = dict?.toObject<T>()

            if (obj != null) list.add(obj)

        }

        return list
    }

    fun updateObjectFields(
        collectionName: String,
        documentId: String,
        updates: Map<String, Any?>
    ): Boolean {

        val collection = couchbaseManager.getCollection(collectionName)

        val existingDoc = collection.getDocument(documentId) ?: return false

        val mutableDoc = existingDoc.toMutable()

        updates.forEach { (key, value) ->

            when (value) {

                is String -> mutableDoc.setString(key, value)

                is Int -> mutableDoc.setInt(key, value)

                is Boolean -> mutableDoc.setBoolean(key, value)

                is Long -> mutableDoc.setLong(key, value)

                is Float -> mutableDoc.setFloat(key, value)

                is Double -> mutableDoc.setDouble(key, value)

                null -> mutableDoc.setValue(key, null)

                else -> mutableDoc.setValue(key, value)
            }

        }

        collection.save(mutableDoc)

        return true
    }

    inline fun <reified T : Any> updateObject(
        collectionName: String,
        documentId: String,
        value: T
    ): Boolean {

        val collection = couchbaseManager.getCollection(collectionName)
        val existingDoc = collection.getDocument(documentId) ?: return false
        val updatedDoc = value.toDocument(documentId)

        collection.save(updatedDoc)

        return true
    }

    fun deleteObject(
        collectionName: String,
        documentId: String
    ): Boolean {
        val collection = couchbaseManager.getCollection(collectionName)
        val doc = collection.getDocument(documentId) ?: return false

        collection.delete(doc)

        return true
    }

}