package dev.septianbeneran.technicaltest.api.pokemon.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.septianbeneran.technicaltest.api.pokemon.data.local.PokemonCache
import dev.septianbeneran.technicaltest.api.pokemon.data.local.PokemonCacheImpl
import dev.septianbeneran.technicaltest.api.pokemon.data.remote.api.PokemonApi
import dev.septianbeneran.technicaltest.api.pokemon.data.remote.service.PokemonApiRemoteDataSource
import dev.septianbeneran.technicaltest.api.pokemon.data.remote.service.PokemonApiRemoteDataSourceImpl
import dev.septianbeneran.technicaltest.api.pokemon.repository.PokemonRepository
import dev.septianbeneran.technicaltest.api.pokemon.repository.PokemonRepositoryImpl
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PokemonApiModule {
    companion object {
        @Provides
        @Singleton
        fun provideApiPokemon(
            retrofit: Retrofit
        ): PokemonApi {
            return retrofit.create(PokemonApi::class.java)
        }
    }

    @Binds
    @Singleton
    abstract fun bindPokemonApiRemoteDataSource(
        pokemonApiRemoteDataSourceImpl: PokemonApiRemoteDataSourceImpl
    ): PokemonApiRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindPokemonRepository(
        pokemonRepositoryImpl: PokemonRepositoryImpl
    ): PokemonRepository

    @Binds
    @Singleton
    abstract fun bindPokemonCache(
        pokemonCacheImpl: PokemonCacheImpl
    ): PokemonCache

}