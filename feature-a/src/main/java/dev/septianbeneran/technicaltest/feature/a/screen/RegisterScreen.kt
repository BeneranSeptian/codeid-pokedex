package dev.septianbeneran.technicaltest.feature.a.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.septianbeneran.technicaltest.core.base.BaseScreen
import dev.septianbeneran.technicaltest.core.navigation.util.Navigator
import dev.septianbeneran.technicaltest.core.ui.component.PokeButton
import dev.septianbeneran.technicaltest.core.ui.component.PokeTextField
import dev.septianbeneran.technicaltest.core.ui.theme.Highlight
import dev.septianbeneran.technicaltest.core.ui.util.EventObserver
import dev.septianbeneran.technicaltest.feature.a.R
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterAction
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterAction.OnConfirmPasswordChange
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterAction.OnEmailChange
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterAction.OnLoginClick
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterAction.OnNameChange
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterAction.OnPasswordChange
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterAction.OnRegisterClick
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterEvent.NavigateToLogin
import dev.septianbeneran.technicaltest.feature.a.screen.properties.RegisterUiState
import dev.septianbeneran.technicaltest.feature.a.viewmodel.RegisterViewModel

@Composable
fun RegisterScreenRoute(
    navigator: Navigator
) {
    val viewModel: RegisterViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BaseScreen(
        viewModel = viewModel,
        contentPadding = PaddingValues(24.dp)
    ) {
        RegisterScreen(
            uiState = uiState,
            onAction = viewModel::onAction
        )
    }

    EventObserver(event = viewModel.event) { event ->
        when (event) {
            NavigateToLogin -> navigator.navigateUp()
        }
    }
}

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onAction: (RegisterAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.register_title),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = stringResource(R.string.register_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        RegisterForm(
            uiState = uiState,
            onAction = onAction,
            modifier = Modifier.weight(1f)
        )

        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.register_already_have_account),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(R.string.register_login_now),
                style = MaterialTheme.typography.bodySmall,
                color = Highlight.Highlight400,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onAction(OnLoginClick) }
            )
        }
    }
}

@Composable
fun RegisterForm(
    uiState: RegisterUiState,
    onAction: (RegisterAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        PokeTextField(
            value = uiState.name,
            onValueChange = { onAction(OnNameChange(it)) },
            label = stringResource(R.string.register_name_label),
            placeholder = stringResource(R.string.register_name_placeholder),
            isError = uiState.nameError != null,
            errorMessage = uiState.nameError?.let { stringResource(it) }
        )

        Spacer(Modifier.height(16.dp))

        PokeTextField(
            value = uiState.email,
            onValueChange = { onAction(OnEmailChange(it)) },
            label = stringResource(R.string.register_email_label),
            placeholder = stringResource(R.string.register_email_placeholder),
            isError = uiState.emailError != null,
            errorMessage = uiState.emailError?.let { stringResource(it) }
        )

        Spacer(Modifier.height(16.dp))

        PokeTextField(
            value = uiState.password,
            onValueChange = { onAction(OnPasswordChange(it)) },
            label = stringResource(R.string.register_password_label),
            isPasswordField = true,
            isError = uiState.passwordError != null,
            errorMessage = uiState.passwordError?.let { stringResource(it) }
        )

        Spacer(Modifier.height(16.dp))

        PokeTextField(
            value = uiState.confirmPassword,
            onValueChange = { onAction(OnConfirmPasswordChange(it)) },
            label = stringResource(R.string.register_confirm_password_label),
            isPasswordField = true,
            isError = uiState.confirmPasswordError != null,
            errorMessage = uiState.confirmPasswordError?.let { stringResource(it) }
        )

        Spacer(Modifier.weight(1f))

        PokeButton(
            text = stringResource(R.string.register_button),
            onClick = { onAction(OnRegisterClick) },
            enabled = uiState.isRegisterEnabled
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(
        uiState = RegisterUiState(),
        onAction = {}
    )
}
