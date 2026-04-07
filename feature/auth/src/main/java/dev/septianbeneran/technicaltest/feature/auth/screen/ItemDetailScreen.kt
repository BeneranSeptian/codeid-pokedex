package dev.septianbeneran.technicaltest.feature.auth.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.septianbeneran.technicaltest.feature.auth.viewmodel.ItemDetailViewModel

@Composable
fun ItemDetailScreen(
    viewModel: ItemDetailViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = viewModel.args.item.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "ID: ${viewModel.args.item.id}",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = viewModel.args.item.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
