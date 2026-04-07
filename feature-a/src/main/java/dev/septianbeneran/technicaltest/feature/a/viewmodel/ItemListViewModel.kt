package dev.septianbeneran.technicaltest.feature.a.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.septianbeneran.technicaltest.api.usecase.GetItemListUseCase
import dev.septianbeneran.technicaltest.api.usecase.LoadItemListUseCase
import dev.septianbeneran.technicaltest.api.usecase.SaveItemListUseCase
import dev.septianbeneran.technicaltest.core.base.BaseViewModel
import dev.septianbeneran.technicaltest.core.entity.model.Item
import dev.septianbeneran.technicaltest.feature.a.screen.properties.ItemListAction
import dev.septianbeneran.technicaltest.feature.a.screen.properties.ItemListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ItemListViewModel @Inject constructor(
    private val getItemListUseCase: GetItemListUseCase,
    private val saveItemListUseCase: SaveItemListUseCase,
    private val loadItemListUseCase: LoadItemListUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(ItemListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        onAction(ItemListAction.FetchItems("69bae88baa77b81da9f806f6"))
        onAction(ItemListAction.LoadLocalItems)
    }

    fun onAction(action: ItemListAction) {
        when (action) {
            is ItemListAction.FetchItems -> getItemList(action.bindId)
            is ItemListAction.SaveItems -> saveItemList(action.items)
            is ItemListAction.LoadLocalItems -> loadLocalItems()
        }
    }

    private fun getItemList(bindId: String) {
        collectApi(
            flow = getItemListUseCase(bindId),
            onLoading = { _uiState.update { it.copy(isLoading = true, error = null) } },
            onSuccess = { items ->
                _uiState.update { it.copy(isLoading = false, items = items ?: emptyList()) }
            },
            onError = { error ->
                _uiState.update { it.copy(isLoading = false, error = error) }
            }
        )
    }

    private fun saveItemList(items: List<Item>) {
        collectLocalData(
            flow = saveItemListUseCase(items),
            onSuccess = { loadLocalItems() }
        )
    }

    private fun loadLocalItems() {
        collectLocalData(
            flow = loadItemListUseCase(),
            onSuccess = { data ->
                _uiState.update { it.copy(localItems = data ?: emptyList()) }
            }
        )
    }
}