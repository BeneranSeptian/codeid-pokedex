package dev.septianbeneran.technicaltest.feature.splash.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.septianbeneran.technicaltest.core.base.BaseNavGraph
import dev.septianbeneran.technicaltest.feature.splash.navigation.SplashNavGraphImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class SplashNavModule {

    @Binds
    @IntoSet
    abstract fun bindSplashNavGraphImpl(navGraph: SplashNavGraphImpl): BaseNavGraph
}