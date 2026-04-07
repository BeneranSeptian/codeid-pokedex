package dev.septianbeneran.technicaltest.core.util

import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}

inline fun <reified T> T.toJson(): String = json.encodeToString(this)

inline fun <reified T> String.fromJson(): T = json.decodeFromString(this)