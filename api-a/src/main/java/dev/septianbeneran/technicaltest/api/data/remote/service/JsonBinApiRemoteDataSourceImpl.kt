package dev.septianbeneran.technicaltest.api.data.remote.service

import dev.septianbeneran.technicaltest.api.data.remote.api.JsonBinApi
import dev.septianbeneran.technicaltest.core.base.BaseDataSource
import javax.inject.Inject

class JsonBinApiRemoteDataSourceImpl @Inject constructor(
    private val api: JsonBinApi
) : JsonBinApiRemoteDataSource, BaseDataSource() {
    override suspend fun getItemList(bindId: String) = getResult {
        api.getItemList("b/$bindId")
    }
}