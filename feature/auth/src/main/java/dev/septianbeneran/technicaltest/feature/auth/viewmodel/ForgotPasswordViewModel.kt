package dev.septianbeneran.technicaltest.feature.auth.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.septianbeneran.technicaltest.api.auth.usecase.UpdatePasswordUseCase
import dev.septianbeneran.technicaltest.api.auth.usecase.UpdatePasswordUseCase.Result.EmailNotRegistered
import dev.septianbeneran.technicaltest.api.auth.usecase.UpdatePasswordUseCase.Result.SameAsCurrentPassword
import dev.septianbeneran.technicaltest.api.auth.usecase.UpdatePasswordUseCase.Result.Success
import dev.septianbeneran.technicaltest.core.base.BaseViewModel
import dev.septianbeneran.technicaltest.core.ui.component.PokeTextFieldValidator.validateEmail
import dev.septianbeneran.technicaltest.core.ui.component.PokeTextFieldValidator.validatePassword
import dev.septianbeneran.technicaltest.feature.auth.R
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.DialogState
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.ForgotPasswordAction
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.ForgotPasswordAction.OnConfirmPasswordChange
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.ForgotPasswordAction.OnDismissDialog
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.ForgotPasswordAction.OnEmailChange
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.ForgotPasswordAction.OnNavigateToRegister
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.ForgotPasswordAction.OnPasswordChange
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.ForgotPasswordAction.OnUpdatePasswordClick
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.ForgotPasswordEvent
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.ForgotPasswordEvent.NavigateToLogin
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.ForgotPasswordEvent.ShowToast
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.ForgotPasswordUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val updatePasswordUseCase: UpdatePasswordUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: ForgotPasswordAction) {
        when (action) {
            is OnEmailChange -> {
                _uiState.update {
                    val isValid = validateEmail(action.email)
                    it.copy(
                        email = action.email,
                        emailError = if (isValid || action.email.isEmpty()) null else R.string.error_invalid_email,
                        isUpdateEnabled = validateFields(action.email, it.password, it.confirmPassword)
                    )
                }
            }

            is OnPasswordChange -> {
                _uiState.update {
                    val isValid = validatePassword(action.password)
                    val doPasswordsMatch = action.password == it.confirmPassword
                    it.copy(
                        password = action.password,
                        passwordError = if (isValid || action.password.isEmpty()) null else R.string.error_invalid_password,
                        confirmPasswordError = if (doPasswordsMatch || it.confirmPassword.isEmpty()) null else R.string.error_password_mismatch,
                        isUpdateEnabled = validateFields(it.email, action.password, it.confirmPassword)
                    )
                }
            }

            is OnConfirmPasswordChange -> {
                _uiState.update {
                    val doPasswordsMatch = action.confirmPassword == it.password
                    it.copy(
                        confirmPassword = action.confirmPassword,
                        confirmPasswordError = if (doPasswordsMatch || action.confirmPassword.isEmpty()) null else R.string.error_password_mismatch,
                        isUpdateEnabled = validateFields(it.email, it.password, action.confirmPassword)
                    )
                }
            }

            is OnUpdatePasswordClick -> {
                val result = updatePasswordUseCase(
                    email = _uiState.value.email,
                    newPassword = _uiState.value.password
                )

                when (result) {
                    Success -> {
                        sendEvent(ShowToast(R.string.forgot_password_success))
                        sendEvent(NavigateToLogin(_uiState.value.email))
                    }

                    EmailNotRegistered -> {
                        _uiState.update { it.copy(dialogState = DialogState.EmailNotRegistered(it.email)) }
                    }

                    SameAsCurrentPassword -> {
                        _uiState.update { it.copy(dialogState = DialogState.SameAsCurrent) }
                    }

                    else -> {}
                }
            }

            is OnDismissDialog -> { _uiState.update { it.copy(dialogState = null) } }

            is OnNavigateToRegister -> {
                _uiState.update { it.copy(dialogState = null) }
                sendEvent(ForgotPasswordEvent.NavigateToRegister(action.email))
            }
        }
    }

    private fun validateFields(email: String, pass: String, confirmPass: String): Boolean {
        return validateEmail(email) &&
                validatePassword(pass) &&
                pass == confirmPass
    }
}
