package dev.septianbeneran.technicaltest.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PokeRed,
    onPrimary = PokeWhite,
    primaryContainer = Color(0xFFFFD5D0),
    onPrimaryContainer = PokeDarkRed,
    secondary = PokeGrey,
    onSecondary = PokeDarkGrey,
    tertiary = PokeDarkRed,
    background = PokeWhite,
    onBackground = PokeDarkGrey,
    surface = PokeWhite,
    onSurface = PokeDarkGrey
)

@Composable
fun TechnicalTestTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}