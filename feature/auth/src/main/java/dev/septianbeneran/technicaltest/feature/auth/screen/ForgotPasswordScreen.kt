package dev.septianbeneran.technicaltest.feature.auth.screen

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.septianbeneran.technicaltest.core.base.BaseScreen
import dev.septianbeneran.technicaltest.core.navigation.route.ForgotPasswordRoute
import dev.septianbeneran.technicaltest.core.navigation.route.LoginRoute
import dev.septianbeneran.technicaltest.core.navigation.route.RegisterRoute
import dev.septianbeneran.technicaltest.core.navigation.util.Navigator
import dev.septianbeneran.technicaltest.core.ui.component.PokeButton
import dev.septianbeneran.technicaltest.core.ui.component.PokeTextField
import dev.septianbeneran.technicaltest.core.ui.theme.Highlight
import dev.septianbeneran.technicaltest.core.ui.util.EventObserver
import dev.septianbeneran.technicaltest.feature.auth.R
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.DialogState
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.ForgotPasswordAction
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.ForgotPasswordUiState
import dev.septianbeneran.technicaltest.feature.auth.screen.properties.ForgotPasswordEvent
import dev.septianbeneran.technicaltest.feature.auth.viewmodel.ForgotPasswordViewModel

@Composable
fun ForgotPasswordScreenRoute(
    navigator: Navigator
) {
    val context = LocalContext.current
    val viewModel: ForgotPasswordViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BaseScreen(
        viewModel = viewModel,
        contentPadding = PaddingValues(0.dp)
    ) {
        ForgotPasswordScreen(
            uiState = uiState,
            onAction = viewModel::onAction
        )
    }

    EventObserver(event = viewModel.event) { event ->
        when (event) {
            is ForgotPasswordEvent.NavigateToLogin -> {
                navigator.navigate(
                    route = LoginRoute(email = event.email),
                    popUpTo = ForgotPasswordRoute,
                    inclusive = true
                )
            }

            is ForgotPasswordEvent.NavigateToRegister -> {
                navigator.navigate(
                    route = RegisterRoute(email = event.email),
                    popUpTo = ForgotPasswordRoute,
                    inclusive = true
                )
            }

            is ForgotPasswordEvent.ShowToast -> {
                Toast.makeText(context, event.message, LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun ForgotPasswordScreen(
    uiState: ForgotPasswordUiState,
    onAction: (ForgotPasswordAction) -> Unit
) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = systemBarsPadding.calculateBottomPadding() + 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    color = Highlight.Highlight100,
                    shape = RoundedCornerShape(bottomStart = 64.dp, bottomEnd = 64.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            ImageSection(
                modifier = Modifier.size(120.dp)
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.forgot_password_title),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = stringResource(R.string.forgot_password_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
            )

            PokeTextField(
                value = uiState.email,
                onValueChange = { onAction(ForgotPasswordAction.OnEmailChange(it)) },
                label = stringResource(R.string.login_email_label),
                placeholder = stringResource(R.string.login_email_placeholder),
                isError = uiState.emailError != null,
                errorMessage = uiState.emailError?.let { stringResource(it) }
            )

            Spacer(Modifier.height(16.dp))

            PokeTextField(
                value = uiState.password,
                onValueChange = { onAction(ForgotPasswordAction.OnPasswordChange(it)) },
                label = stringResource(R.string.forgot_password_new_password_label),
                isPasswordField = true,
                isError = uiState.passwordError != null,
                errorMessage = uiState.passwordError?.let { stringResource(it) }
            )

            Spacer(Modifier.height(16.dp))

            PokeTextField(
                value = uiState.confirmPassword,
                onValueChange = { onAction(ForgotPasswordAction.OnConfirmPasswordChange(it)) },
                label = stringResource(R.string.forgot_password_confirm_password_label),
                isPasswordField = true,
                isError = uiState.confirmPasswordError != null,
                errorMessage = uiState.confirmPasswordError?.let { stringResource(it) }
            )

            Spacer(modifier = Modifier.height(40.dp))

            PokeButton(
                text = stringResource(R.string.forgot_password_button),
                onClick = { onAction(ForgotPasswordAction.OnUpdatePasswordClick) },
                enabled = uiState.isUpdateEnabled
            )
        }
    }

    uiState.dialogState?.let { dialogState ->
        when (dialogState) {
            DialogState.SameAsCurrent -> {
                AlertDialog(
                    onDismissRequest = { onAction(ForgotPasswordAction.OnDismissDialog) },
                    title = { Text(stringResource(R.string.common_error)) },
                    text = { Text(stringResource(R.string.forgot_password_error_same_password)) },
                    confirmButton = {
                        TextButton(onClick = { onAction(ForgotPasswordAction.OnDismissDialog) }) {
                            Text(stringResource(R.string.common_close))
                        }
                    }
                )
            }

            is DialogState.EmailNotRegistered -> {
                AlertDialog(
                    onDismissRequest = { onAction(ForgotPasswordAction.OnDismissDialog) },
                    title = { Text(stringResource(R.string.common_error)) },
                    text = { Text(stringResource(R.string.forgot_password_error_email_not_registered)) },
                    confirmButton = {
                        TextButton(onClick = { onAction(ForgotPasswordAction.OnNavigateToRegister(dialogState.email)) }) {
                            Text(stringResource(R.string.forgot_password_go_to_registration))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { onAction(ForgotPasswordAction.OnDismissDialog) }) {
                            Text(stringResource(R.string.common_close))
                        }
                    }
                )
            }
        }
    }
}
