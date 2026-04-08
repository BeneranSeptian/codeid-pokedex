package dev.septianbeneran.technicaltest.api.pokemon.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.septianbeneran.technicaltest.api.pokemon.data.local.PokemonCache
import dev.septianbeneran.technicaltest.api.pokemon.data.remote.service.PokemonApiRemoteDataSource
import dev.septianbeneran.technicaltest.api.pokemon.paging.PokemonPagingSource
import dev.septianbeneran.technicaltest.core.base.BaseRepository
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.Pokemon
import dev.septianbeneran.technicaltest.core.util.CoroutineDispatcherProvider
import dev.septianbeneran.technicaltest.core.util.resultFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remote: PokemonApiRemoteDataSource,
    private val local: PokemonCache,
    private val dispatcher: CoroutineDispatcherProvider
) : PokemonRepository, BaseRepository() {

    override fun getPokemonPaging(pageSize: Int, query: String?): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PokemonPagingSource(remote, local, query) }
        ).flow
    }

    override fun getPokemonDetail(nameOrId: String) = resultFlow(
        networkCall = { remote.getPokemonDetail(nameOrId) },
        dispatcher = dispatcher,
        transform = { it?.mapToEntity() },
        loadFromLocal = { local.loadPokemonDetail(nameOrId) },
        saveToLocal = { it?.let { local.updatePokemonDetail(it) } }
    )
}