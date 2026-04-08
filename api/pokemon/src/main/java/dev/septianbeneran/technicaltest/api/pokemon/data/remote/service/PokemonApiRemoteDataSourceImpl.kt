package dev.septianbeneran.technicaltest.api.pokemon.data.remote.service

import dev.septianbeneran.technicaltest.api.pokemon.data.remote.api.PokemonApi
import dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.PokemonDto
import dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.PokemonResponse
import dev.septianbeneran.technicaltest.core.base.BaseDataSource
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult
import javax.inject.Inject

class PokemonApiRemoteDataSourceImpl @Inject constructor(
    private val api: PokemonApi
) : PokemonApiRemoteDataSource,
    BaseDataSource() {
    override suspend fun getPokemonList() = getResult {
        api.getPokemonList("pokemon")
    }
}