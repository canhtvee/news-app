package com.example.newsapp.data.repositories

import android.util.Log
import com.example.newsapp.utils.Resource
import com.example.newsapp.data.models.ApiResponse
import com.example.newsapp.data.models.Article
import kotlinx.coroutines.flow.*

abstract class BaseRepository {

    fun performGetOperation2(
        getDataFromRemoteSource: suspend () -> List<Resource<ApiResponse>>,
        saveDataToDatabase: suspend (List<Article>) -> Unit,
        getDataFromLocalSource: () -> Flow<List<Article>>
    ) = flow {

        Log.d("_BaseRepository", "call performGetOperation2")
        emit(Resource.Loading())

        val localData = getDataFromLocalSource().first()
        Log.d("_BaseRepository", "localData: $localData")
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


    fun performGetOperation(
        getDataFromRemoteSource: suspend () -> Resource<ApiResponse>,
        saveDataToDatabase: suspend (List<Article>) -> Unit,
        getDataFromLocalSource: () -> Flow<List<Article>>
    ) = flow<Resource<List<Article>>> {

        emit(Resource.Loading())

        val localData = getDataFromLocalSource().first()
        if (localData.isEmpty()) {
            val response = getDataFromRemoteSource.invoke()
            when (response) {
                is Resource.Success -> {
                    saveDataToDatabase(response.data.articles)
                    emitAll(getDataFromLocalSource().map { data -> Resource.Success(data) })
                }
                is Resource.Error -> {
                    emit(Resource.Error(response.message))
                }
            }
        } else {

            emitAll(getDataFromLocalSource().map { data -> Resource.Success(data) })

        }
    }

    /*   fun <L> performGetOperation(
        getDataFromRemoteSource: suspend () -> Resource<ApiResponse>,
        saveDataToDatabase: suspend (List<Article>) -> Unit,
        getDataFromLocalSource: () -> Flow<L>
    ) = flow<Resource<L>> {

        emit(Resource.Loading())

        val localData = getDataFromLocalSource().first()
        Log.d("First Item", localData.toString())

        if (localData ==) {
            val response = getDataFromRemoteSource.invoke()
            when (response) {
                is Resource.Success -> {
                    Log.d("Data", response.data.articles.toString())
                    saveDataToDatabase(response.data.articles)
                    emitAll(getDataFromLocalSource().map { data -> Resource.Success(data) })
                }
                is Resource.Error -> {
                    emit(Resource.Error(response.message))
                }
            }
        } else {

            emitAll(getDataFromLocalSource().map { data -> Resource.Success(data) })

        }
    }

    */
}