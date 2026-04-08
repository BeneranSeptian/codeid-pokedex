package dev.septianbeneran.technicaltest.feature.pokemon.screen.properties

import dev.septianbeneran.technicaltest.core.base.BaseEvent
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.Pokemon

data class PokemonListUiState(
    val pokemonList: List<Pokemon> = emptyList(),
    val filteredPokemonList: List<Pokemon> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val offset: Int = 0,
    val isLastPage: Boolean = false
)

sealed interface PokemonListAction {
    data object GetPokemonList : PokemonListAction
    data object LoadNextPage : PokemonListAction
    data class OnSearchQueryChange(val query: String) : PokemonListAction
    data class OnPokemonClick(val pokemon: Pokemon) : PokemonListAction
}

sealed interface PokemonListEvent: BaseEvent {
    data class NavigateToDetail(val id: Int, val name: String) : PokemonListEvent
}