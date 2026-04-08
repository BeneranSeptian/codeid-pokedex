package dev.septianbeneran.technicaltest.core.navigation.route.auth

import dev.septianbeneran.technicaltest.core.entity.model.Item
import kotlinx.serialization.Serializable

@Serializable
data class ItemDetailRoute(
    val item: Item
)

