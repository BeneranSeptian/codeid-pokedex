package dev.septianbeneran.technicaltest.core.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    viewModel: BaseViewModel,
    contentPadding: PaddingValues = PaddingValues(24.dp),
    content: @Composable () -> Unit
) {
    val uiState = viewModel.baseScreenUiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        content()
    }
}