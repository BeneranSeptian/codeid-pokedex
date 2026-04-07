package dev.septianbeneran.technicaltest.feature.auth.screen.properties

import dev.septianbeneran.technicaltest.core.entity.model.Item
import dev.septianbeneran.technicaltest.core.entity.remote.ErrorResponse

data class ItemListUiState(
    val isLoading: Boolean = false,
    val items: List<Item> = emptyList(),
    val error: ErrorResponse? = null,
    val localItems: List<Item> = emptyList()
)

sealed interface ItemListAction {
    data class FetchItems(val bindId: String) : ItemListAction
    data class SaveItems(val items: List<Item>) : ItemListAction
    data object LoadLocalItems : ItemListAction
}
