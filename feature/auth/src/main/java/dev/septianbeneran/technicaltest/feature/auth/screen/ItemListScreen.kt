package dev.septianbeneran.technicaltest.feature.auth.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.septianbeneran.technicaltest.core.entity.model.Item
import dev.septianbeneran.technicaltest.feature.auth.viewmodel.ItemListViewModel
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.ItemListAction
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.ItemListUiState

@Composable
fun ItemListRoute(
    onItemClick: (Item) -> Unit,
    viewModel: ItemListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    ItemListScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        onItemClick = onItemClick
    )
}

@Composable
internal fun ItemListScreen(
    uiState: ItemListUiState,
    onAction: (ItemListAction) -> Unit,
    onItemClick: (Item) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

        uiState.error?.let {
            Text(text = it.message.orEmpty())
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(uiState.items) { item ->
                ItemRow(
                    itemResponse = item,
                    onClick = { onItemClick(item) }
                )
                HorizontalDivider()
            }

            if (uiState.items.isNotEmpty()) {
                item {
                    Button(
                        onClick = { onAction(ItemListAction.SaveItems(uiState.items)) },
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text(text = "Save to Local")
                    }
                }
            }
            
            if (uiState.localItems.isNotEmpty()) {
                item {
                    Text(
                        text = "Local Items:",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                items(uiState.localItems) { item ->
                    ItemRow(
                        itemResponse = item,
                        onClick = { onItemClick(item) }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun ItemRow(
    itemResponse: Item,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        Text(text = itemResponse.name, style = MaterialTheme.typography.titleMedium)
        Text(text = itemResponse.description, style = MaterialTheme.typography.bodySmall)
    }
}
