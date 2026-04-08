package dev.septianbeneran.technicaltest.feature.pokemon.screen.properties

import dev.septianbeneran.technicaltest.core.entity.model.pokemon.Pokemon

data class PokemonListUiState(
    val pokemons: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface PokemonListAction {
    data object GetPokemonList : PokemonListAction
    data class OnPokemonClick(val pokemon: Pokemon) : PokemonListAction
}