package dev.septianbeneran.technicaltest.core.util

import com.couchbase.lite.Dictionary
import com.couchbase.lite.Document
import com.couchbase.lite.MutableDocument
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

inline fun <reified T : Any> T.toDocument(
    documentId: String
): MutableDocument {

    val doc = MutableDocument(documentId)

    val properties = T::class.memberProperties

    properties.forEach { prop ->
        val name = prop.name
        when (val value = prop.get(this)) {
            is String ->
                doc.setString(name, value)

            is Int -> doc.setInt(name, value)

            is Boolean -> doc.setBoolean(name, value)

            is Long -> doc.setLong(name, value)

            is Float -> doc.setFloat(name, value)

            is Double -> doc.setDouble(name, value)

            null -> {}

            else -> {
                @Suppress("UNCHECKED_CAST")
                doc.setString(name, json.encodeToString(serializer(prop.returnType) as KSerializer<Any>, value))
            }
        }
    }

    return doc
}

inline fun <reified T : Any> Document.toObject(): T? {

    val constructor =
        T::class.primaryConstructor
            ?: return null

    val args = constructor.parameters.associateWith { param ->

        val name = param.name ?: return@associateWith null

        when (param.type.classifier) {

            String::class -> getString(name)

            Int::class -> getInt(name)

            Boolean::class -> getBoolean(name)

            Long::class -> getLong(name)

            Float::class -> getFloat(name)

            Double::class -> getDouble(name)

            else -> {
                getString(name)?.let {
                    runCatching { json.decodeFromString(serializer(param.type), it) }.getOrNull()
                }
            }
        }
    }

    return constructor.callBy(args)

}

inline fun <reified T : Any> Dictionary.toObject(): T? {

    val constructor = T::class.primaryConstructor ?: return null

    val args =
        constructor.parameters.associateWith { param ->

            val name = param.name ?: return@associateWith null

            when (param.type.classifier) {

                String::class -> getString(name)

                Int::class -> getInt(name)

                Boolean::class -> getBoolean(name)

                Long::class -> getLong(name)

                Float::class -> getFloat(name)

                Double::class -> getDouble(name)

                else ->
                    getString(name)?.let {
                        runCatching { json.decodeFromString(serializer(param.type), it) }.getOrNull()
                    }
            }

        }

    return constructor.callBy(args)
}