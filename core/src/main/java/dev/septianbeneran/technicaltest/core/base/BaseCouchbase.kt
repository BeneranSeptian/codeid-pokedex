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

}