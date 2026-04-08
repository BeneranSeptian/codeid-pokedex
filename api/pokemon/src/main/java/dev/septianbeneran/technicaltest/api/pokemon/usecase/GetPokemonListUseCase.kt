package dev.septianbeneran.technicaltest.api.pokemon.usecase

import androidx.paging.PagingData
import dev.septianbeneran.technicaltest.api.pokemon.repository.PokemonRepository
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.Pokemon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
){
    operator fun invoke(pageSize: Int = 10, query: String? = null): Flow<PagingData<Pokemon>> =
        repository.getPokemonPaging(pageSize, query)
}