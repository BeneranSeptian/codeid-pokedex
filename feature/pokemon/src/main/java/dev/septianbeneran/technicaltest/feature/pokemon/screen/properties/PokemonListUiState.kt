package dev.septianbeneran.technicaltest.feature.pokemon.screen.properties

import dev.septianbeneran.technicaltest.core.base.BaseEvent
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.Pokemon

data class PokemonListUiState(
    val searchQuery: String = "",
    val error: String? = null
)

sealed interface PokemonListAction {
    data class OnSearchQueryChange(val query: String) : PokemonListAction
    data class OnPokemonClick(val pokemon: Pokemon) : PokemonListAction
}

sealed interface PokemonListEvent: BaseEvent {
    data class NavigateToDetail(val id: Int, val name: String) : PokemonListEvent
}