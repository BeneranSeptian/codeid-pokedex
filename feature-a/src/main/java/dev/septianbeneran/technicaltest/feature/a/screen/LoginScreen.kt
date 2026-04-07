package dev.septianbeneran.technicaltest.feature.a.screen

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.septianbeneran.technicaltest.core.base.BaseScreen
import dev.septianbeneran.technicaltest.core.navigation.route.ItemListRoute
import dev.septianbeneran.technicaltest.core.navigation.route.LoginRoute
import dev.septianbeneran.technicaltest.core.navigation.route.RegisterRoute
import dev.septianbeneran.technicaltest.core.ui.component.PokeButton
import dev.septianbeneran.technicaltest.core.ui.component.PokeMediaPlaceHolder
import dev.septianbeneran.technicaltest.core.ui.component.PokeTextField
import dev.septianbeneran.technicaltest.core.navigation.util.Navigator
import dev.septianbeneran.technicaltest.core.ui.theme.Highlight
import dev.septianbeneran.technicaltest.core.ui.util.EventObserver
import dev.septianbeneran.technicaltest.feature.a.R
import dev.septianbeneran.technicaltest.feature.a.screen.properties.LoginAction
import dev.septianbeneran.technicaltest.feature.a.screen.properties.LoginAction.OnEmailChange
import dev.septianbeneran.technicaltest.feature.a.screen.properties.LoginAction.OnLoginClick
import dev.septianbeneran.technicaltest.feature.a.screen.properties.LoginAction.OnPasswordChange
import dev.septianbeneran.technicaltest.feature.a.screen.properties.LoginEvent
import dev.septianbeneran.technicaltest.feature.a.screen.properties.LoginEvent.NavigateToForgotPassword
import dev.septianbeneran.technicaltest.feature.a.screen.properties.LoginEvent.NavigateToHome
import dev.septianbeneran.technicaltest.feature.a.screen.properties.LoginEvent.NavigateToRegister
import dev.septianbeneran.technicaltest.feature.a.screen.properties.LoginEvent.ShowToast
import dev.septianbeneran.technicaltest.feature.a.screen.properties.LoginUiState
import dev.septianbeneran.technicaltest.feature.a.viewmodel.LoginViewModel

@Composable
fun LoginScreenRoute(
    navigator: Navigator
) {
    val context = LocalContext.current
    val viewModel: LoginViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BaseScreen(
        viewModel = viewModel,
        contentPadding = PaddingValues(0.dp)
    ) {
        LoginScreen(
            uiState = uiState,
            onAction = viewModel::onAction,
            onEvent = viewModel::sendEvent
        )
    }

    EventObserver(
        event = viewModel.event
    ) { event ->
        when (event) {
            is NavigateToForgotPassword -> {
                // TODO: Navigate to ForgotPassword screen
            }

            is NavigateToRegister -> {
                navigator.navigate(RegisterRoute)
            }

            is NavigateToHome -> {
                navigator.navigate(
                    route = ItemListRoute,
                    popUpTo = LoginRoute,
                    inclusive = true
                )
            }

            is ShowToast -> {
                Toast.makeText(context, event.message, LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onAction: (LoginAction) -> Unit,
    onEvent: (LoginEvent) -> Unit
) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ImageSection(
            modifier = Modifier.weight(1f).fillMaxSize()
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = systemBarsPadding.calculateBottomPadding() + 24.dp),
        ) {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = stringResource(R.string.login_welcome),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )

            AuthSection(
                emailValue = uiState.email,
                emailError = uiState.emailError,
                passwordValue = uiState.password,
                passwordError = uiState.passwordError,
                isLoginEnabled = uiState.isLoginEnabled,
                onEmailValueChange = { onAction(OnEmailChange(it)) },
                onPasswordValueChange = { onAction(OnPasswordChange(it)) },
                onLoginClick = { onAction(OnLoginClick(uiState.email, uiState.password)) },
                onForgotPasswordClick = { onEvent(NavigateToForgotPassword) },
                onRegisterClick = { onEvent(NavigateToRegister) }
            )
        }
    }
}

@Composable
fun ImageSection(
    modifier: Modifier = Modifier
) {
    PokeMediaPlaceHolder(
        modifier = modifier
    )
}

@Composable
fun AuthSection(
    modifier: Modifier = Modifier,
    emailValue: String,
    @StringRes emailError: Int? = null,
    passwordValue: String,
    @StringRes passwordError: Int? = null,
    isLoginEnabled: Boolean = false,
    onEmailValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        Column() {
            PokeTextField(
                value = emailValue,
                onValueChange = onEmailValueChange,
                label = stringResource(R.string.login_email_label),
                placeholder = stringResource(R.string.login_email_placeholder),
                isError = emailError != null,
                errorMessage = emailError?.let { stringResource(it) }
            )

            Spacer(Modifier.height(16.dp))

            PokeTextField(
                value = passwordValue,
                onValueChange = onPasswordValueChange,
                label = stringResource(R.string.login_password_label),
                isPasswordField = true,
                isError = passwordError != null,
                errorMessage = passwordError?.let { stringResource(it) }
            )

            Text(
                text = stringResource(R.string.login_forgot_password),
                style = MaterialTheme.typography.bodySmall,
                color = Highlight.Highlight400,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clickable(
                        enabled = true,
                        onClick = onForgotPasswordClick
                    )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        PokeButton(
            text = stringResource(R.string.login_button),
            onClick = onLoginClick,
            enabled = isLoginEnabled
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.login_not_a_member),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(R.string.login_register_now),
                style = MaterialTheme.typography.bodySmall,
                color = Highlight.Highlight400,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onRegisterClick() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        uiState = LoginUiState(),
        onAction = {},
        onEvent = {}
    )
}
