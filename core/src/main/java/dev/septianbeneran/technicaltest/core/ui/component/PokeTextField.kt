package dev.septianbeneran.technicaltest.core.ui.component

import android.util.Patterns.EMAIL_ADDRESS
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PokeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isPasswordField: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = label?.let { { Text(it) } },
            placeholder = placeholder?.let { { Text(it) } },
            isError = isError,
            enabled = enabled,
            readOnly = readOnly,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = if (isPasswordField && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            trailingIcon = {
                if (isPasswordField) {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                } else {
                    trailingIcon?.invoke()
                }
            },
            singleLine = true,
            shape = MaterialTheme.shapes.medium
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

object PokeTextFieldValidator {
    fun validateEmail(email: String): Boolean {
        return EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 6
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun PokeTextFieldPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            PokeTextField(
                value = "",
                onValueChange = {},
                label = "Email",
                placeholder = "Enter your email"
            )

            PokeTextField(
                value = "invalid-email",
                onValueChange = {},
                label = "Email",
                isError = true,
                errorMessage = "Invalid email format",
                modifier = Modifier.padding(top = 16.dp)
            )

            PokeTextField(
                value = "password123",
                onValueChange = {},
                label = "Password",
                isPasswordField = true,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
