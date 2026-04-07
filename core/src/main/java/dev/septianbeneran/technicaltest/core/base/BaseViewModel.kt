package dev.septianbeneran.technicaltest.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.septianbeneran.technicaltest.core.entity.local.LocalResult
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult
import dev.septianbeneran.technicaltest.core.entity.remote.ErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {
    private val _showCentralLoading = MutableStateFlow(false)
    val showCentralLoading = _showCentralLoading.asStateFlow()

    open fun <T : Any> collectApi(
        flow: Flow<ApiResult<T>>,
        isCentralLoading: Boolean? = null,
        onError: ((ErrorResponse) -> Unit)? = null,
        onLoading: (() -> Unit)? = null,
        onSuccess: ((T?) -> Unit)? = null,
    ) {
        viewModelScope.launch {
            flow.distinctUntilChanged().collect { result ->
                when (result) {
                    is ApiResult.Error -> {
                        if(isCentralLoading != null) _showCentralLoading.value = false
                        onError?.invoke(result.error)
                    }
                    is ApiResult.Loading -> {
                        if(isCentralLoading != null) _showCentralLoading.value = true
                        onLoading?.invoke()
                    }
                    is ApiResult.Success -> {
                        if(isCentralLoading != null) _showCentralLoading.value = false
                        onSuccess?.invoke(result.data)
                    }
                }
            }
        }
    }

    open fun <T: Any> collectLocalData(
        flow: Flow<LocalResult<T>>,
        onError: ((String) -> Unit)? = null,
        onSuccess: ((T?) -> Unit)? = null,
        onEmpty: (()-> Unit)? = null
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
}