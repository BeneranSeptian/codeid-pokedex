package dev.septianbeneran.technicaltest.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.septianbeneran.technicaltest.core.ui.theme.Highlight

@Composable
fun PokeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = Highlight.Highlight500,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.5f),
            disabledContentColor = contentColor.copy(alpha = 0.5f)
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PokeButtonPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            PokeButton(
                text = "Primary Button",
                onClick = {},
                modifier = Modifier.padding(bottom = 8.dp)
            )
            PokeButton(
                text = "Disabled Button",
                onClick = {},
                enabled = false
            )
        }
    }
}
