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

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) { articleLocalDataSource.deleteAll() }
    }
    suspend fun deleteByTags(tags : List<String>) {
        withContext(Dispatchers.IO) { articleLocalDataSource.deleteByTags(tags )}
    }

    fun getTagsHeadline() = performGetOperation2(
        getDataFromRemoteSource = { articleRemoteDataSource.getForMultiTags(sourcePlanning.tagList) },
        saveDataToDatabase = { articleLocalDataSource.insert(it) },
        getDataFromLocalSource = { articleLocalDataSource.loadByContent(sourcePlanning.tagList) }
    ).flowOn(Dispatchers.IO)




}