package com.example.newsapp.data.repositories

import android.util.Log
import com.example.newsapp.utils.Resource
import com.example.newsapp.data.models.ApiResponse
import com.example.newsapp.data.models.Article
import kotlinx.coroutines.flow.*

abstract class BaseRepository {

    fun performGetOperation(
        getDataFromRemoteSource: suspend () -> List<Resource<ApiResponse>>,
        saveDataToDatabase: suspend (List<Article>) -> Unit,
        getDataFromLocalSource: () -> Flow<List<Article>>
    ) = flow {

        emit(Resource.Loading())

        val localData = getDataFromLocalSource().first()

        if (localData.isEmpty()) {
            val response = getDataFromRemoteSource.invoke()
            val success = mutableListOf<Boolean>()
            response.forEach { resp ->
                when (resp) {
                    is Resource.Success -> {
                        saveDataToDatabase(resp.data.articles)
                        success.add(true)
                    }
                    else -> success.add(false)
                }
            }
            emitAll(getDataFromLocalSource().map { data -> Resource.Success(data) })
        } else {
            emitAll(getDataFromLocalSource().map { data -> Resource.Success(data) })
        }
    }
}