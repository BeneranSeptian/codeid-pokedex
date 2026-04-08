package dev.septianbeneran.technicaltest.feature.home.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.septianbeneran.technicaltest.core.base.BaseNavGraph
import dev.septianbeneran.technicaltest.feature.home.navigation.HomeNavGraphImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeNavModule {

    @Binds
    @IntoSet
    abstract fun bindHomeNavGraphImpl(navGraph: HomeNavGraphImpl): BaseNavGraph
}