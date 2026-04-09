package dev.septianbeneran.technicaltest.feature.pokemon.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.septianbeneran.technicaltest.api.pokemon.usecase.GetPokemonDetailUseCase
import dev.septianbeneran.technicaltest.api.pokemon.usecase.GetPokemonListUseCase
import dev.septianbeneran.technicaltest.core.base.BaseViewModel
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.Pokemon
import dev.septianbeneran.technicaltest.feature.pokemon.screen.properties.PokemonListAction
import dev.septianbeneran.technicaltest.feature.pokemon.screen.properties.PokemonListEvent
import dev.septianbeneran.technicaltest.feature.pokemon.screen.properties.PokemonListUiState
import dev.septianbeneran.technicaltest.feature.pokemon.util.extractPokemonId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(PokemonListUiState())
    val uiState = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    val pokemonPagingData: Flow<PagingData<Pokemon>> = _searchQuery
        .debounce(300L)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getPokemonListUseCase(query = query.takeIf { it.isNotEmpty() })
        }
        .cachedIn(viewModelScope)

    fun onAction(action: PokemonListAction) {
        when (action) {
            is PokemonListAction.OnSearchQueryChange -> {
                _uiState.update { it.copy(searchQuery = action.query) }
                _searchQuery.value = action.query
            }

            is PokemonListAction.OnPokemonClick -> {
                sendEvent(
                    PokemonListEvent.NavigateToDetail(
                        id = action.pokemon.url.extractPokemonId(),
                        name = action.pokemon.name
                    )
                )
            }

            is PokemonListAction.OnSearchDetailClick -> {
                val query = _uiState.value.searchQuery
                if (query.isNotEmpty()) {
                    collectApi(
                        flow = getPokemonDetailUseCase(query),
                        isCentralLoading = true,
                        onSuccess = { pokemon ->
                            if (pokemon != null) {
                                sendEvent(
                                    PokemonListEvent.NavigateToDetail(
                                        id = pokemon.id,
                                        name = pokemon.name
                                    )
                                )
                            } else {
                                _uiState.update { it.copy(searchDetailError = "Pokemon not found") }
                            }
                        },
                        onError = { error ->
                            _uiState.update { it.copy(searchDetailError = error.message) }
                        }
                    )
                }
            }

            is PokemonListAction.DismissSearchDetailError -> {
                _uiState.update { it.copy(searchDetailError = null) }
            }
        }
    }
}
