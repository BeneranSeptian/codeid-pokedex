package dev.septianbeneran.technicaltest.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.septianbeneran.technicaltest.core.entity.local.LocalResult
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult
import dev.septianbeneran.technicaltest.core.entity.remote.ErrorResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {
    private val _baseScreenUiState = MutableStateFlow(BaseScreenUiState())
    val baseScreenUiState = _baseScreenUiState.asStateFlow()

    private val _event = Channel<BaseEvent>(BUFFERED)
    val event = _event.receiveAsFlow()

    open fun <T : Any> collectApi(
        flow: Flow<ApiResult<T?>>,
        isCentralLoading: Boolean? = null,
        onError: ((ErrorResponse) -> Unit)? = null,
        onLoading: (() -> Unit)? = null,
        onSuccess: ((T?) -> Unit)? = null,
    ) {
        viewModelScope.launch {
            flow.distinctUntilChanged().collect { result ->
                when (result) {
                    is ApiResult.Error -> {
                        if (isCentralLoading != null) _baseScreenUiState.update {
                            it.copy(showCentralLoading = false)
                        }
                        onError?.invoke(result.error)
                    }

                    is ApiResult.Loading -> {
                        if (isCentralLoading != null) _baseScreenUiState.update {
                            it.copy(showCentralLoading = true)
                        }
                        onLoading?.invoke()
                    }

                    is ApiResult.Success -> {
                        if (isCentralLoading != null) _baseScreenUiState.update {
                            it.copy(showCentralLoading = false)
                        }
                        onSuccess?.invoke(result.data)
                    }
                }
            }
        }
    }

    open fun <T : Any> collectLocalData(
        flow: Flow<LocalResult<T>>,
        onError: ((String) -> Unit)? = null,
        onSuccess: ((T?) -> Unit)? = null,
        onEmpty: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            flow.distinctUntilChanged().collect { localResult ->
                when (localResult) {
                    is LocalResult.Error -> {
                        onError?.invoke(localResult.errorMessage)
                    }

                    is LocalResult.Success -> {
                        onSuccess?.invoke(localResult.data)
                    }

                    is LocalResult.Empty -> onEmpty?.invoke()
                }
            }
        }
    }

    fun sendEvent(event: BaseEvent) = viewModelScope.launch { _event.send(event) }
}