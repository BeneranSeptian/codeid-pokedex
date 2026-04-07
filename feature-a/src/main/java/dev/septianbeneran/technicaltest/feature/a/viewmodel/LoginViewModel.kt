package dev.septianbeneran.technicaltest.feature.a.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.septianbeneran.technicaltest.api.usecase.LoginUserUseCase
import dev.septianbeneran.technicaltest.core.base.BaseViewModel
import dev.septianbeneran.technicaltest.core.ui.component.PokeTextFieldValidator.validateEmail
import dev.septianbeneran.technicaltest.core.ui.component.PokeTextFieldValidator.validatePassword
import dev.septianbeneran.technicaltest.feature.a.R
import dev.septianbeneran.technicaltest.feature.a.screen.properties.LoginAction
import dev.septianbeneran.technicaltest.feature.a.screen.properties.LoginAction.OnEmailChange
import dev.septianbeneran.technicaltest.feature.a.screen.properties.LoginAction.OnLoginClick
import dev.septianbeneran.technicaltest.feature.a.screen.properties.LoginAction.OnPasswordChange
import dev.septianbeneran.technicaltest.feature.a.screen.properties.LoginEvent.NavigateToHome
import dev.septianbeneran.technicaltest.feature.a.screen.properties.LoginEvent.ShowToast
import dev.septianbeneran.technicaltest.feature.a.screen.properties.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

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