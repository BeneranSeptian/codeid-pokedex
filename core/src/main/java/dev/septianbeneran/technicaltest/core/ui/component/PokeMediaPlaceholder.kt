package dev.septianbeneran.technicaltest.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.septianbeneran.technicaltest.core.R

@Composable
fun PokeMediaPlaceholder(
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    icon: ImageVector = Icons.Default.QuestionMark,
    iconSize: Dp = 48.dp,
    alpha: Float = 0.3f
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.pokemon_silhouette),
            contentDescription = null,
            modifier = Modifier
                .size(size)
                .alpha(alpha),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.outline)
        )
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(iconSize),
            tint = MaterialTheme.colorScheme.outline
        )
    }
}
