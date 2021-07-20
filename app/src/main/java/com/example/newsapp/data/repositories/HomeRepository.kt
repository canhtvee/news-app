package com.example.newsapp.data.repositories

import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.remote.ArticleRemoteDataSource
import com.example.newsapp.utils.SourcePlanning
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val articleLocalDataSource: ArticleDao,
    private val sourcePlanning: SourcePlanning
) : BaseRepository() {

    suspend fun deleteByTags(tags : List<String>) : Boolean {
        withContext(Dispatchers.IO) { articleLocalDataSource.deleteByTags(tags)}
        return true
    }
    fun getTagsHeadline() = performGetOperation(
        getDataFromRemoteSource = { articleRemoteDataSource.getForMultiTags(sourcePlanning.tagList) },
        saveDataToDatabase = { articleLocalDataSource.insert(it) },
        getDataFromLocalSource = { articleLocalDataSource.loadByTags(sourcePlanning.tagList) }
    ).flowOn(Dispatchers.IO)
}