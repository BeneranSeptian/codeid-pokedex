package dev.septianbeneran.technicaltest.api.pokemon.repository

import dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.PokemonDto
import dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.PokemonResponse
import dev.septianbeneran.technicaltest.api.pokemon.data.remote.service.PokemonApiRemoteDataSource
import dev.septianbeneran.technicaltest.core.base.BaseRepository
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult
import dev.septianbeneran.technicaltest.core.util.CoroutineDispatcherProvider
import dev.septianbeneran.technicaltest.core.util.resultFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remote: PokemonApiRemoteDataSource,
    private val dispatcher: CoroutineDispatcherProvider
) : PokemonRepository, BaseRepository() {
    override fun getPokemonList() = resultFlow(
        networkCall = { remote.getPokemonList() },
        dispatcher = dispatcher
    ).mapToEntity{
        it?.results?.map { pokemon -> pokemon.mapToEntity() }
    }
}