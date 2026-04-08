package dev.septianbeneran.technicaltest.feature.pokemon.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import dev.septianbeneran.technicaltest.core.base.BaseScreen
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.Pokemon
import dev.septianbeneran.technicaltest.core.navigation.route.pokemon.PokemonDetailRoute
import dev.septianbeneran.technicaltest.core.navigation.util.Navigator
import dev.septianbeneran.technicaltest.core.ui.util.EventObserver
import dev.septianbeneran.technicaltest.core.ui.util.shimmerEffect
import dev.septianbeneran.technicaltest.core.R as CoreR
import dev.septianbeneran.technicaltest.feature.pokemon.R
import dev.septianbeneran.technicaltest.feature.pokemon.screen.properties.PokemonListAction
import dev.septianbeneran.technicaltest.feature.pokemon.screen.properties.PokemonListEvent.NavigateToDetail
import dev.septianbeneran.technicaltest.feature.pokemon.util.extractPokemonId
import dev.septianbeneran.technicaltest.feature.pokemon.util.getPokemonImageUrl
import dev.septianbeneran.technicaltest.feature.pokemon.viewmodel.PokemonListViewModel
import java.util.Locale

@Composable
fun PokemonListScreenRoute(
    navigator: Navigator
) {
    val viewModel: PokemonListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val pokemonPagingItems = viewModel.pokemonPagingData.collectAsLazyPagingItems()

    BaseScreen(
        viewModel = viewModel,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.onAction(PokemonListAction.OnSearchQueryChange(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text(stringResource(R.string.search_pokemon)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                )
            )

            Box(modifier = Modifier.weight(1f)) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        count = pokemonPagingItems.itemCount,
                        key = pokemonPagingItems.itemKey { it.name },
                        contentType = pokemonPagingItems.itemContentType { "pokemon" }
                    ) { index ->
                        val pokemon = pokemonPagingItems[index]
                        if (pokemon != null) {
                            PokemonItem(
                                pokemon = pokemon,
                                searchQuery = uiState.searchQuery,
                                onClick = { viewModel.onAction(PokemonListAction.OnPokemonClick(pokemon)) }
                            )
                        }
                    }

                    when (val loadState = pokemonPagingItems.loadState.append) {
                        is LoadState.Loading -> {
                            item {
                                LoadingItem()
                            }
                        }

                        is LoadState.Error -> {
                            item {
                                ErrorItem(
                                    message = loadState.error.message ?: "Unknown error",
                                    onRetry = { pokemonPagingItems.retry() }
                                )
                            }
                        }

                        else -> {}
                    }

                    if (pokemonPagingItems.loadState.refresh is LoadState.Error) {
                        item {
                            val error = pokemonPagingItems.loadState.refresh as LoadState.Error
                            ErrorItem(
                                message = error.error.message ?: "Unknown error",
                                onRetry = { pokemonPagingItems.retry() }
                            )
                        }
                    }

                    if (pokemonPagingItems.loadState.refresh is LoadState.Loading) {
                        items(10) {
                            PokemonItemSkeleton()
                        }
                    }
                }
            }
        }
    }

    EventObserver(
        event = viewModel.event
    ) { event ->
        when (event) {
            is NavigateToDetail -> {
                navigator.navigate(PokemonDetailRoute(pokemonId = event.id, pokemonName = event.name))
            }
        }
    }
}

@Composable
fun PokemonItemSkeleton() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(24.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
            }
        }
    }
}

@Composable
fun LoadingItem() {
    PokemonItemSkeleton()
}

@Composable
fun ErrorItem(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.retry),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun PokemonItem(
    pokemon: Pokemon,
    searchQuery: String = "",
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = getPokemonImageUrl(pokemon.url),
                    contentDescription = pokemon.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit,
                    error = painterResource(id = CoreR.drawable.pokemon_silhouette),
                    placeholder = painterResource(id = CoreR.drawable.pokemon_silhouette)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "#${pokemon.url.extractPokemonId().toString().padStart(3, '0')}",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )

                val pokemonName = pokemon.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }

                val annotatedName = if (searchQuery.isNotEmpty() && pokemonName.contains(searchQuery, ignoreCase = true)) {
                    val startIndex = pokemonName.lowercase(Locale.getDefault()).indexOf(searchQuery.lowercase(Locale.getDefault()))
                    val endIndex = startIndex + searchQuery.length

                    buildAnnotatedString {
                        append(pokemonName.substring(0, startIndex))
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.ExtraBold)) {
                            append(pokemonName.substring(startIndex, endIndex))
                        }
                        append(pokemonName.substring(endIndex))
                    }
                } else {
                    buildAnnotatedString { append(pokemonName) }
                }

                Text(
                    text = annotatedName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
