package dev.septianbeneran.technicaltest.api.pokemon.repository

import dev.septianbeneran.technicaltest.api.pokemon.data.remote.service.PokemonApiRemoteDataSource
import dev.septianbeneran.technicaltest.core.base.BaseRepository
import dev.septianbeneran.technicaltest.core.util.CoroutineDispatcherProvider
import dev.septianbeneran.technicaltest.core.util.resultFlow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remote: PokemonApiRemoteDataSource,
    private val dispatcher: CoroutineDispatcherProvider
) : PokemonRepository, BaseRepository() {
    override fun getPokemonList(limit: Int, offset: Int) = resultFlow(
        networkCall = { remote.getPokemonList(limit, offset) },
        dispatcher = dispatcher
    ).mapToEntity {
        it?.results?.map { pokemon -> pokemon.mapToEntity() }
    }

    override fun getPokemonDetail(nameOrId: String) = resultFlow(
        networkCall = { remote.getPokemonDetail(nameOrId) },
        dispatcher = dispatcher
    ).mapToEntity {
        it?.mapToEntity()
    }
}