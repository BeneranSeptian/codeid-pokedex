package dev.septianbeneran.technicaltest.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> EventObserver(
    event: Flow<T>,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(event, lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(STARTED) {
            event.collect(onEvent)
        }
    }
}