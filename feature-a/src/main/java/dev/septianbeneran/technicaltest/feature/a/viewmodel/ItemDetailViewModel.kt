package dev.septianbeneran.technicaltest.feature.a.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.septianbeneran.technicaltest.core.base.BaseViewModel
import dev.septianbeneran.technicaltest.feature.a.navigation.getItemDetailRouteParams
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): BaseViewModel() {
    val args = savedStateHandle.getItemDetailRouteParams()
}