package dev.septianbeneran.technicaltest.feature.pokemon.screen.properties

import dev.septianbeneran.technicaltest.core.entity.model.pokemon.PokemonDetail

data class PokemonDetailUiState(
    val pokemon: PokemonDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface PokemonDetailAction {
    data class LoadPokemon(val nameOrId: String) : PokemonDetailAction
}
