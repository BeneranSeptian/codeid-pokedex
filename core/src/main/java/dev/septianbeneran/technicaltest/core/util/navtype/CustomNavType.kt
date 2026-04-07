package dev.septianbeneran.technicaltest.core.util.navtype

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class SerializableNavType<T : Any>(
    private val serializer: KSerializer<T>,
    private val json: Json = Json
) : NavType<T>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): T? {
        return bundle.getString(key)?.let {
            json.decodeFromString(serializer, it)
        }
    }

    override fun parseValue(value: String): T {
        return json.decodeFromString(
            serializer,
            Uri.decode(value)
        )
    }

    override fun serializeAsValue(value: T): String {
        return Uri.encode(
            json.encodeToString(serializer, value)
        )
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(
            key,
            json.encodeToString(serializer, value)
        )
    }
}

inline fun <reified T : Any> serializableNavType(
    json: Json = Json
): NavType<T> {
    return SerializableNavType(
        serializer = serializer(),
        json = json
    )
}

inline fun <reified T : Any> typeMapOf(): Pair<KType, NavType<T>> {
    val type = typeOf<T>()
    return type to serializableNavType<T>()
}