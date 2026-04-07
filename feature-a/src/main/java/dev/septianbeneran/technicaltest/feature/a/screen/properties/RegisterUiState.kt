package dev.septianbeneran.technicaltest.feature.a.screen.properties

import androidx.annotation.StringRes
import dev.septianbeneran.technicaltest.core.base.BaseEvent

data class RegisterUiState(
    val name: String = "",
    @StringRes val nameError: Int? = null,
    val email: String = "",
    @StringRes val emailError: Int? = null,
    val password: String = "",
    @StringRes val passwordError: Int? = null,
    val confirmPassword: String = "",
    @StringRes val confirmPasswordError: Int? = null,
    val isRegisterEnabled: Boolean = false
)

sealed interface RegisterAction {
    data class OnNameChange(val name: String) : RegisterAction
    data class OnEmailChange(val email: String) : RegisterAction
    data class OnPasswordChange(val password: String) : RegisterAction
    data class OnConfirmPasswordChange(val confirmPassword: String) : RegisterAction
    data object OnRegisterClick : RegisterAction
    data object OnLoginClick : RegisterAction
}

sealed interface RegisterEvent : BaseEvent {
    data object NavigateToLogin : RegisterEvent
}
