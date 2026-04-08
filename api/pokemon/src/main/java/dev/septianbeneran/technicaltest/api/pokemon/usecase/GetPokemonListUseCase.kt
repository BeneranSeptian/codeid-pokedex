package dev.septianbeneran.technicaltest.api.pokemon.usecase

import dev.septianbeneran.technicaltest.api.pokemon.repository.PokemonRepository
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.Pokemon
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
){
    operator fun invoke(limit: Int = 10, offset: Int = 0): Flow<ApiResult<List<Pokemon>>> =
        repository.getPokemonList(limit, offset)

}