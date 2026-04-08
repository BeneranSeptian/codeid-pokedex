package dev.septianbeneran.technicaltest.feature.pokemon.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.septianbeneran.technicaltest.api.pokemon.usecase.GetPokemonDetailUseCase
import dev.septianbeneran.technicaltest.core.base.BaseViewModel
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult
import dev.septianbeneran.technicaltest.core.navigation.route.pokemon.PokemonDetailRoute
import dev.septianbeneran.technicaltest.feature.pokemon.screen.properties.PokemonDetailAction
import dev.septianbeneran.technicaltest.feature.pokemon.screen.properties.PokemonDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val route = savedStateHandle.toRoute<PokemonDetailRoute>()

    private val _uiState = MutableStateFlow(PokemonDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        onAction(PokemonDetailAction.LoadPokemon(route.pokemonId.toString()))
    }

    fun onAction(action: PokemonDetailAction) {
        when (action) {
            is PokemonDetailAction.LoadPokemon -> loadPokemon(action.nameOrId)
        }
    }

    private fun loadPokemon(nameOrId: String) {
        getPokemonDetailUseCase(nameOrId).onEach { result ->
            when (result) {
                is ApiResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true, error = null) }
                }

                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            pokemon = result.data
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
