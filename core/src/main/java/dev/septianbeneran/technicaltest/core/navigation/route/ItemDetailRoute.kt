package dev.septianbeneran.technicaltest.core.navigation.route

import dev.septianbeneran.technicaltest.core.entity.model.Item
import kotlinx.serialization.Serializable

@Serializable
data class ItemDetailRoute(
    val item: Item
)

