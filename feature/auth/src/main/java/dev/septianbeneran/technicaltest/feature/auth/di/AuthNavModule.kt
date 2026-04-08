package dev.septianbeneran.technicaltest.feature.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.septianbeneran.technicaltest.core.base.BaseNavGraph
import dev.septianbeneran.technicaltest.feature.auth.navigation.AuthNavGraphImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthNavModule {

    @Binds
    @IntoSet
    abstract fun bindAuthNavGraphImpl(navGraph: AuthNavGraphImpl): BaseNavGraph
}