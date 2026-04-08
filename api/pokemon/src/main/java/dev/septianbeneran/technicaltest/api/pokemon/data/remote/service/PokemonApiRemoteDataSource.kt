package dev.septianbeneran.technicaltest.api.pokemon.data.remote.service

import dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.PokemonDto
import dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.PokemonResponse
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult

interface PokemonApiRemoteDataSource {
    suspend fun getPokemonList(): ApiResult<PokemonDto<List<PokemonResponse>>>
}