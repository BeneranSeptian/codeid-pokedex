package dev.septianbeneran.technicaltest.feature.auth.screen.properties

import androidx.annotation.StringRes
import dev.septianbeneran.technicaltest.core.base.BaseEvent

data class ForgotPasswordUiState(
    val email: String = "",
    @StringRes val emailError: Int? = null,
    val password: String = "",
    @StringRes val passwordError: Int? = null,
    val confirmPassword: String = "",
    @StringRes val confirmPasswordError: Int? = null,
    val isUpdateEnabled: Boolean = false,
    val dialogState: DialogState? = null
)

sealed interface ForgotPasswordAction {
    data class OnEmailChange(val email: String) : ForgotPasswordAction
    data class OnPasswordChange(val password: String) : ForgotPasswordAction
    data class OnConfirmPasswordChange(val confirmPassword: String) : ForgotPasswordAction
    data object OnUpdatePasswordClick : ForgotPasswordAction
    data object OnDismissDialog : ForgotPasswordAction
    data class OnNavigateToRegister(val email: String) : ForgotPasswordAction
}

sealed interface ForgotPasswordEvent : BaseEvent {
    data class NavigateToLogin(val email: String) : ForgotPasswordEvent
    data class NavigateToRegister(val email: String) : ForgotPasswordEvent
    data class ShowToast(@StringRes val message: Int) : ForgotPasswordEvent
}

sealed interface DialogState {
    data object SameAsCurrent : DialogState
    data class EmailNotRegistered(val email: String) : DialogState
}
