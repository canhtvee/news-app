package com.example.newsapp.data.repositories

import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.models.Article
import com.example.newsapp.data.remote.ArticleRemoteDataSource
import com.example.newsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HeadlineRepository @Inject constructor(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val articleLocalDataSource: ArticleDao,
) : BaseRepository() {

    suspend fun deleteByTags(tags: List<String>): Boolean {
        withContext(Dispatchers.IO) { articleLocalDataSource.deleteByTags(tags) }
        return true
    }

    fun getHeadline(tags: List<String>): Flow<Resource<List<Article>>> {
        return performGetOperation(
            getDataFromRemoteSource = { articleRemoteDataSource.getFromMultiSources(tags) },
            saveDataToDatabase = { articleLocalDataSource.insert(it) },
            getDataFromLocalSource = { articleLocalDataSource.loadByTags(tags) }
        ).flowOn(Dispatchers.IO)
    }
}
