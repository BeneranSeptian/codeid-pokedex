package dev.septianbeneran.technicaltest.api.pokemon.data.remote.api

import dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.PokemonDto
import dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonApi {
    @GET
    suspend fun getPokemonList(
        @Url url: String
    ): Response<PokemonDto<List<PokemonResponse>>>
}