package com.example.newsapp.data.repositories

import android.util.Log
import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.models.Article
import com.example.newsapp.data.remote.ArticleRemoteDataSource
import com.example.newsapp.utils.Resource
import com.example.newsapp.utils.SourcePlanning
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HeadlineRepository @Inject constructor(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val articleLocalDataSource: ArticleDao,
    private val sourcePlanning: SourcePlanning
) : BaseRepository() {

    suspend fun deleteByTags(tags : List<String>) : Boolean {
        withContext(Dispatchers.IO) { articleLocalDataSource.deleteByTags(tags)}
        return true
    }

    fun getBusinessHeadline() : Flow<Resource<List<Article>>> {
        return performGetOperation(
            getDataFromRemoteSource = { articleRemoteDataSource.getFromMultiSources(sourcePlanning.businessSources) },
            saveDataToDatabase = { articleLocalDataSource.insert(it) },
            getDataFromLocalSource = { articleLocalDataSource.loadByTags(sourcePlanning.businessSources) }
        ).flowOn(Dispatchers.IO)
    }

    fun getTechHeadline() = performGetOperation(
        getDataFromRemoteSource = { articleRemoteDataSource.getFromMultiSources(sourcePlanning.techSources) },
        saveDataToDatabase = { articleLocalDataSource.insert(it) },
        getDataFromLocalSource = { articleLocalDataSource.loadByTags(sourcePlanning.techSources) }
    ).flowOn(Dispatchers.IO)

    fun getStartupHeadline() = performGetOperation(
        getDataFromRemoteSource = { articleRemoteDataSource.getForMultiTags(sourcePlanning.startup) },
        saveDataToDatabase = { articleLocalDataSource.insert(it) },
        getDataFromLocalSource = { articleLocalDataSource.loadByTags(sourcePlanning.startup) }
    ).flowOn(Dispatchers.IO)

    fun getScienceHeadline() = performGetOperation(
        getDataFromRemoteSource = { articleRemoteDataSource.getFromMultiSources(sourcePlanning.scienceSources) },
        saveDataToDatabase = { articleLocalDataSource.insert(it) },
        getDataFromLocalSource = { articleLocalDataSource.loadByTags(sourcePlanning.scienceSources) }
    ).flowOn(Dispatchers.IO)

    fun getLifeHeadline() = performGetOperation(
        getDataFromRemoteSource = { articleRemoteDataSource.getFromMultiSources(sourcePlanning.lifeSources) },
        saveDataToDatabase = { articleLocalDataSource.insert(it) },
        getDataFromLocalSource = { articleLocalDataSource.loadByTags(sourcePlanning.lifeSources) }
    ).flowOn(Dispatchers.IO)

}