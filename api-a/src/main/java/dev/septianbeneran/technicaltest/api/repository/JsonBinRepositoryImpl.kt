package dev.septianbeneran.technicaltest.api.repository

import dev.septianbeneran.technicaltest.api.data.local.service.JsonBinCacheDataSource
import dev.septianbeneran.technicaltest.api.data.remote.service.JsonBinApiRemoteDataSource
import dev.septianbeneran.technicaltest.core.base.BaseRepository
import dev.septianbeneran.technicaltest.core.util.CoroutineDispatcherProvider
import dev.septianbeneran.technicaltest.core.util.resultFlow
import javax.inject.Inject

class JsonBinRepositoryImpl @Inject constructor(
    private val remote: JsonBinApiRemoteDataSource,
    private val dispatcher: CoroutineDispatcherProvider,
    override val cacheDataSource: JsonBinCacheDataSource
) : JsonBinRepository, BaseRepository() {
    override fun getItemList(bindId: String) = resultFlow(
        networkCall = { remote.getItemList(bindId) },
        dispatcher = dispatcher
    ).mapToEntity {
        it?.record?.map { item -> item.mapToEntity() }
    }
}

