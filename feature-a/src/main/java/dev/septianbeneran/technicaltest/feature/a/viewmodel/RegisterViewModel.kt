package dev.septianbeneran.technicaltest.feature.a.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.septianbeneran.technicaltest.api.usecase.RegisterUserUseCase
import dev.septianbeneran.technicaltest.core.base.BaseViewModel
import dev.septianbeneran.technicaltest.core.entity.model.User
import dev.septianbeneran.technicaltest.core.ui.component.PokeTextFieldValidator.validateEmail
import dev.septianbeneran.technicaltest.core.ui.component.PokeTextFieldValidator.validatePassword
import dev.septianbeneran.technicaltest.feature.a.R
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterAction
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterAction.OnConfirmPasswordChange
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterAction.OnEmailChange
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterAction.OnLoginClick
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterAction.OnNameChange
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterAction.OnPasswordChange
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterAction.OnRegisterClick
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterEvent.NavigateToHome
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterEvent.NavigateToLogin
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterEvent.ShowToast
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            is OnNameChange -> {
                _uiState.update {
                    it.copy(
                        name = action.name,
                        nameError = if (action.name.isNotEmpty() || it.name.isEmpty()) null else R.string.error_empty_name,
                        isRegisterEnabled = validateFields(action.name, it.email, it.password, it.confirmPassword)
                    )
                }
            }

            is OnEmailChange -> {
                _uiState.update {
                    val isEmailValid = validateEmail(action.email)
                    it.copy(
                        email = action.email,
                        emailError = if (isEmailValid || action.email.isEmpty()) null else R.string.error_invalid_email,
                        isRegisterEnabled = validateFields(it.name, action.email, it.password, it.confirmPassword)
                    )
                }
            }

            is OnPasswordChange -> {
                _uiState.update {
                    val isPasswordValid = validatePassword(action.password)
                    val doPasswordsMatch = action.password == it.confirmPassword
                    it.copy(
                        password = action.password,
                        passwordError = if (isPasswordValid || action.password.isEmpty()) null else R.string.error_invalid_password,
                        confirmPasswordError = if (doPasswordsMatch || it.confirmPassword.isEmpty()) null else R.string.error_password_mismatch,
                        isRegisterEnabled = validateFields(it.name, it.email, action.password, it.confirmPassword)
                    )
                }
            }

            is OnConfirmPasswordChange -> {
                _uiState.update {
                    val doPasswordsMatch = action.confirmPassword == it.password
                    it.copy(
                        confirmPassword = action.confirmPassword,
                        confirmPasswordError = if (doPasswordsMatch || action.confirmPassword.isEmpty()) null else R.string.error_password_mismatch,
                        isRegisterEnabled = validateFields(it.name, it.email, it.password, action.confirmPassword)
                    )
                }
            }

            is OnRegisterClick -> {
                val user = User(
                    name = uiState.value.name,
                    email = uiState.value.email,
                    password = uiState.value.password
                )

                if (uiState.value.isRegisterEnabled) {
                    val isSuccess = registerUserUseCase(user)
                    if (isSuccess) {
                        sendEvent(NavigateToHome)
                    } else {
                        sendEvent(ShowToast(R.string.error_email_already_registered))
                    }
                }
            }

            is OnLoginClick -> {
                sendEvent(NavigateToLogin)
            }
        }
    }

    private fun validateFields(name: String, email: String, pass: String, confirmPass: String): Boolean {
        return name.isNotEmpty() &&
                validateEmail(email) &&
                validatePassword(pass) &&
                pass == confirmPass
    }
}
