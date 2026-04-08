package dev.septianbeneran.technicaltest.feature.profile.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.septianbeneran.technicaltest.core.base.BaseNavGraph
import dev.septianbeneran.technicaltest.feature.profile.navigation.ProfileNavGraphImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileNavModule {

    @Binds
    @IntoSet
    abstract fun bindProfileNavGraphImpl(navGraph: ProfileNavGraphImpl): BaseNavGraph
}