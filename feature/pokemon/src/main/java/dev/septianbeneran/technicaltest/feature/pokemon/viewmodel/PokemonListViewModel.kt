package dev.septianbeneran.technicaltest.feature.pokemon.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.septianbeneran.technicaltest.api.pokemon.usecase.GetPokemonListUseCase
import dev.septianbeneran.technicaltest.core.base.BaseViewModel
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult
import dev.septianbeneran.technicaltest.feature.pokemon.screen.properties.PokemonListAction
import dev.septianbeneran.technicaltest.feature.pokemon.screen.properties.PokemonListEvent
import dev.septianbeneran.technicaltest.feature.pokemon.screen.properties.PokemonListUiState
import dev.septianbeneran.technicaltest.feature.pokemon.util.extractPokemonId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(PokemonListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        onAction(PokemonListAction.GetPokemonList)
    }

    fun onAction(action: PokemonListAction) {
        when (action) {
            is PokemonListAction.GetPokemonList -> getPokemonList()
            is PokemonListAction.OnSearchQueryChange -> {
                _uiState.update {
                    it.copy(
                        searchQuery = action.query,
                        filteredPokemonList = if (action.query.isEmpty()) {
                            it.pokemonList
                        } else {
                            it.pokemonList.filter { pokemon ->
                                pokemon.name.contains(action.query, ignoreCase = true)
                            }
                        }
                    )
                }
            }

            is PokemonListAction.OnPokemonClick -> {
                sendEvent(
                    PokemonListEvent.NavigateToDetail(
                        id = action.pokemon.url.extractPokemonId(),
                        name = action.pokemon.name
                    )
                )
            }
        }
    }

    private fun getPokemonList() {
        getPokemonListUseCase().onEach { result ->
            when (result) {
                is ApiResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true, error = null) }
                }

                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            pokemonList = result.data ?: emptyList(),
                            filteredPokemonList = result.data ?: emptyList()
                        )
                    }
                }

                is ApiResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.error.message
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}
