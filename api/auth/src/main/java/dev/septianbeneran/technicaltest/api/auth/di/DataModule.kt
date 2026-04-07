package dev.septianbeneran.technicaltest.api.auth.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.septianbeneran.technicaltest.api.auth.data.local.cache.AuthCache
import dev.septianbeneran.technicaltest.api.auth.data.local.cache.AuthCacheImpl
import dev.septianbeneran.technicaltest.api.auth.data.local.cache.JsonBinCache
import dev.septianbeneran.technicaltest.api.auth.data.local.cache.JsonBinCacheImpl
import dev.septianbeneran.technicaltest.api.auth.data.local.service.JsonBinCacheDataSource
import dev.septianbeneran.technicaltest.api.auth.data.local.service.JsonBinCacheDataSourceImpl
import dev.septianbeneran.technicaltest.api.auth.data.remote.api.JsonBinApi
import dev.septianbeneran.technicaltest.api.auth.data.remote.service.JsonBinApiRemoteDataSource
import dev.septianbeneran.technicaltest.api.auth.data.remote.service.JsonBinApiRemoteDataSourceImpl
import dev.septianbeneran.technicaltest.api.auth.repository.JsonBinRepository
import dev.septianbeneran.technicaltest.api.auth.repository.JsonBinRepositoryImpl
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    companion object {
        @Provides
        @Singleton
        fun provideApiJsonBin(
            retrofit: Retrofit
        ): JsonBinApi {
            return retrofit.create(JsonBinApi::class.java)
        }
    }

    @Binds
    @Singleton
    abstract fun bindJsonBinRemoteDataSource(
        jsonBinApiRemoteDataSourceImpl: JsonBinApiRemoteDataSourceImpl
    ): JsonBinApiRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindJsonBinRepository(
        itemRepositoryImpl: JsonBinRepositoryImpl
    ): JsonBinRepository

    @Binds
    @Singleton
    abstract fun bindJsonBinCache(
        jsonBinCacheImpl: JsonBinCacheImpl
    ): JsonBinCache

    @Binds
    @Singleton
    abstract fun bindJsonBinCacheDataSource(
        jsonBinCacheDataSourceImpl: JsonBinCacheDataSourceImpl
    ): JsonBinCacheDataSource

    @Binds
    @Singleton
    abstract fun bindAuthCache(authCacheImpl: AuthCacheImpl): AuthCache
}
