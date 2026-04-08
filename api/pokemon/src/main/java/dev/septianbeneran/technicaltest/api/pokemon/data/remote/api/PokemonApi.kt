package dev.septianbeneran.technicaltest.api.pokemon.data.remote.api

import dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.PokemonDetailResponse
import dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.PokemonDto
import dev.septianbeneran.technicaltest.api.pokemon.data.remote.dto.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonDto<List<PokemonResponse>>>

    @GET
    suspend fun getPokemonListByUrl(
        @Url url: String
    ): Response<PokemonDto<List<PokemonResponse>>>

    @GET("pokemon/{nameOrId}")
    suspend fun getPokemonDetail(
        @Path("nameOrId") nameOrId: String
    ): Response<PokemonDetailResponse>
}
