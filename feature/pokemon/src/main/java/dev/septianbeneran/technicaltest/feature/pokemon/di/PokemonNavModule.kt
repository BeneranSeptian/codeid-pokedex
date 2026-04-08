package dev.septianbeneran.technicaltest.feature.pokemon.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.septianbeneran.technicaltest.core.base.BaseNavGraph
import dev.septianbeneran.technicaltest.feature.pokemon.navigation.PokemonNavGraphImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class PokemonNavModule {

    @Binds
    @IntoSet
    abstract fun bindPokemonNavGraphImpl(navGraph: PokemonNavGraphImpl): BaseNavGraph
}