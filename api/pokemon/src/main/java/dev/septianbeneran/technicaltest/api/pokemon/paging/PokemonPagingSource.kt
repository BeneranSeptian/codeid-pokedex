package dev.septianbeneran.technicaltest.api.pokemon.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.septianbeneran.technicaltest.api.pokemon.data.local.PokemonCache
import dev.septianbeneran.technicaltest.api.pokemon.data.remote.service.PokemonApiRemoteDataSource
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.Pokemon
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult

class PokemonPagingSource(
    private val remote: PokemonApiRemoteDataSource,
    private val local: PokemonCache,
    private val query: String? = null
) : PagingSource<Int, Pokemon>() {

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(state.config.pageSize)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(state.config.pageSize)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val offset = params.key ?: 0
        val limit = params.loadSize

        return try {
            if (!query.isNullOrEmpty()) {
                val cachedList = local.loadPokemonList()
                val filteredList = cachedList.filter { it.name.contains(query, ignoreCase = true) }
                
                val end = (offset + limit).coerceAtMost(filteredList.size)
                val results = if (offset < filteredList.size) {
                    filteredList.subList(offset, end)
                } else emptyList()

                LoadResult.Page(
                    data = results,
                    prevKey = if (offset == 0) null else offset - limit,
                    nextKey = if (offset + limit >= filteredList.size) null else offset + limit
                )
            } else {
                val response = remote.getPokemonList(limit, offset)
                
                if (response is ApiResult.Success) {
                    val pokemonList = response.data?.results?.map { it.mapToEntity() } ?: emptyList()
                    local.updatePokemonList(pokemonList)
                    
                    LoadResult.Page(
                        data = pokemonList,
                        prevKey = if (offset == 0) null else offset - limit,
                        nextKey = if (pokemonList.isEmpty()) null else offset + limit
                    )
                } else {
                    val cachedList = local.loadPokemonList()
                    val end = (offset + limit).coerceAtMost(cachedList.size)
                    val localData = if (offset < cachedList.size) {
                        cachedList.subList(offset, end)
                    } else emptyList()

                    if (localData.isNotEmpty()) {
                        LoadResult.Page(
                            data = localData,
                            prevKey = if (offset == 0) null else offset - limit,
                            nextKey = if (localData.size < limit) null else offset + limit
                        )
                    } else {
                        val error = (response as? ApiResult.Error)?.error
                        LoadResult.Error(Exception(error?.message ?: "Unknown error"))
                    }
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
