package dev.septianbeneran.technicaltest.feature.pokemon.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.septianbeneran.technicaltest.core.base.BaseScreen
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.PokemonDetail
import dev.septianbeneran.technicaltest.core.navigation.util.Navigator
import dev.septianbeneran.technicaltest.core.ui.util.shimmerEffect
import dev.septianbeneran.technicaltest.feature.pokemon.R
import dev.septianbeneran.technicaltest.feature.pokemon.screen.properties.PokemonDetailAction
import dev.septianbeneran.technicaltest.feature.pokemon.util.getPokemonOfficialArtWorkUrl
import dev.septianbeneran.technicaltest.feature.pokemon.util.toPokemonTypeColor
import dev.septianbeneran.technicaltest.feature.pokemon.viewmodel.PokemonDetailViewModel
import dev.septianbeneran.technicaltest.core.R as CoreR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreenRoute(
    navigator: Navigator
) {
    val viewModel: PokemonDetailViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    val primaryType = uiState.pokemon?.types?.firstOrNull()?.type?.name ?: "normal"
    val themeColor = remember(primaryType) { primaryType.toPokemonTypeColor() }

    BaseScreen(
        viewModel = viewModel,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = viewModel.route.pokemonName.replaceFirstChar { it.titlecase() },
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "#${viewModel.route.pokemonId.toString().padStart(3, '0')}",
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = themeColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) {
        when {
            uiState.isLoading -> {
                PokemonDetailSkeleton(themeColor = themeColor)
            }

            uiState.error != null || uiState.pokemon == null -> {
                PokemonDetailErrorContent(
                    message = uiState.error ?: stringResource(R.string.pokemon_not_found),
                    onRetry = {
                        viewModel.onAction(PokemonDetailAction.LoadPokemon(viewModel.route.pokemonId.toString()))
                    }
                )
            }

            else -> {
                uiState.pokemon?.let { pokemon ->
                    PokemonDetailContent(pokemon = pokemon, themeColor = themeColor)
                }
            }
        }
    }
}

@Composable
fun PokemonDetailErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = CoreR.drawable.pokemon_silhouette),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp),
            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.3f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.oops),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Composable
fun PokemonDetailSkeleton(themeColor: Color) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(themeColor.copy(alpha = 0.5f))
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .shimmerEffect()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .align(Alignment.Start)
                .width(100.dp)
                .height(20.dp)
                .shimmerEffect()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            repeat(2) {
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .width(80.dp)
                        .height(30.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .shimmerEffect()
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .align(Alignment.Start)
                .width(100.dp)
                .height(20.dp)
                .shimmerEffect()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            repeat(2) {
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .width(100.dp)
                        .height(30.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .shimmerEffect()
                )
            }
        }
    }
}

@Composable
fun PokemonDetailContent(pokemon: PokemonDetail, themeColor: Color) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(themeColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = CoreR.drawable.pokeball),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(200.dp)
                    .offset(x = 100.dp),
                tint = Color.White.copy(alpha = 0.2f)
            )
            AsyncImage(
                model = getPokemonOfficialArtWorkUrl(pokemon.id),
                contentDescription = pokemon.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InfoCard(
                label = stringResource(R.string.height),
                value = "${pokemon.height / 10.0} m",
                modifier = Modifier.weight(1f),
                themeColor = themeColor
            )
            Spacer(modifier = Modifier.width(16.dp))
            InfoCard(
                label = stringResource(R.string.weight),
                value = "${pokemon.weight / 10.0} kg",
                modifier = Modifier.weight(1f),
                themeColor = themeColor
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.types),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            pokemon.types.forEach { typeSlot ->
                val typeColor = typeSlot.type.name.toPokemonTypeColor()
                Text(
                    text = typeSlot.type.name.replaceFirstChar { it.titlecase() },
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .background(typeColor, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.abilities),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            pokemon.abilities.forEach { abilitySlot ->
                Row(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .background(themeColor.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = abilitySlot.ability.name.replaceFirstChar { it.titlecase() },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    if (abilitySlot.isHidden) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .background(themeColor, RoundedCornerShape(4.dp))
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "H",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.base_stats),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))
        pokemon.stats.forEach { stat ->
            StatBar(name = stat.stat.name, value = stat.baseStat, themeColor = themeColor)
        }
    }
}

@Composable
fun InfoCard(
    label: String,
    value: String,
    themeColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(themeColor.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun StatBar(
    name: String,
    value: Int,
    themeColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = name.uppercase().replace("-", " "),
            modifier = Modifier.width(100.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = value.toString().padStart(3, '0'),
            modifier = Modifier.width(35.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
        ) {
            LinearProgressIndicator(
                progress = { value / 255f },
                modifier = Modifier.fillMaxSize(),
                color = themeColor,
                gapSize = 0.dp,
                trackColor = themeColor.copy(alpha = 0.2f)
            )
        }
    }
}
