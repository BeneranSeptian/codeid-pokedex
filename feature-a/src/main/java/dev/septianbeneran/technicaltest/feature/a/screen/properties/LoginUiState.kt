package dev.septianbeneran.technicaltest.feature.a.screen.properties

import androidx.annotation.StringRes
import dev.septianbeneran.technicaltest.core.base.BaseEvent

data class LoginUiState(
    val email: String = "",
    @StringRes val emailError: Int? = null,
    val password: String = "",
    @StringRes val passwordError: Int? = null,
    val isLoginEnabled: Boolean = false
)

sealed interface LoginAction {
    data class OnEmailChange(val email: String) : LoginAction
    data class OnPasswordChange(val password: String) : LoginAction
    data class OnLoginClick(val email: String, val password: String) : LoginAction
}

sealed interface LoginEvent : BaseEvent {
    data object NavigateToForgotPassword : LoginEvent
    data object NavigateToRegister : LoginEvent
    data object NavigateToHome : LoginEvent
    data class ShowToast(@StringRes val message: Int) : LoginEvent
}