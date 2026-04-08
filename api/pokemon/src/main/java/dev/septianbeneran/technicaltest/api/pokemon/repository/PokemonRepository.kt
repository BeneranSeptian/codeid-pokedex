package dev.septianbeneran.technicaltest.api.pokemon.repository

import androidx.paging.PagingData
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.Pokemon
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.PokemonDetail
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonPaging(pageSize: Int, query: String? = null): Flow<PagingData<Pokemon>>
    fun getPokemonDetail(nameOrId: String): Flow<ApiResult<PokemonDetail>>
}