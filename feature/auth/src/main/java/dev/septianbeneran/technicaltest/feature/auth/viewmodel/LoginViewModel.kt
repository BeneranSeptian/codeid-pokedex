package dev.septianbeneran.technicaltest.feature.auth.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.septianbeneran.technicaltest.api.auth.usecase.LoginUserUseCase
import dev.septianbeneran.technicaltest.feature.auth.R
import dev.septianbeneran.technicaltest.core.base.BaseViewModel
import dev.septianbeneran.technicaltest.core.navigation.route.auth.LoginRoute
import dev.septianbeneran.technicaltest.core.ui.component.PokeTextFieldValidator.validateEmail
import dev.septianbeneran.technicaltest.core.ui.component.PokeTextFieldValidator.validatePassword
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.LoginAction
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.LoginAction.OnEmailChange
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.LoginAction.OnLoginClick
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.LoginAction.OnPasswordChange
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.LoginEvent.NavigateToHome
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.LoginEvent.ShowToast
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val route = savedStateHandle.toRoute<LoginRoute>()
        route.email?.let { email ->
            _uiState.update { it.copy(email = email) }
        }
    }

    fun onAction(
        action: LoginAction
    ) {
        when (action) {
            is OnEmailChange -> {
                _uiState.update {
                    val isEmailValid = validateEmail(action.email)
                    it.copy(
                        email = action.email,
                        emailError = if (isEmailValid || action.email.isEmpty()) null else R.string.error_invalid_email,
                        isLoginEnabled = isEmailValid && validatePassword(it.password)
                    )
                }
            }

            is OnPasswordChange -> {
                _uiState.update {
                    val isPasswordValid = validatePassword(action.password)
                    it.copy(
                        password = action.password,
                        passwordError = if (isPasswordValid || action.password.isEmpty()) null else R.string.error_invalid_password,
                        isLoginEnabled = validateEmail(it.email) && isPasswordValid
                    )
                }
            }

            is OnLoginClick -> {
                viewModelScope.launch {
                    val isSuccess = loginUserUseCase(action.email, action.password)
                    if (isSuccess) {
                        sendEvent(NavigateToHome)
                    } else {
                        sendEvent(ShowToast(R.string.error_invalid_credentials))
                    }
                }
            }
        }
    }
}
