package com.canhtv.ee.newsapp.data.repositories

import com.canhtv.ee.newsapp.data.local.ArticleDao
import com.canhtv.ee.newsapp.data.remote.ArticleRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ExploreTopicRepository @Inject constructor(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val articleLocalDataSource: ArticleDao,
) : BaseRepository() {

    fun getSourceHeadline(sourceId: String) = performGetOperation(
        getDataFromRemoteSource = { articleRemoteDataSource.getFromMultiSources(listOf(sourceId)) },
        saveDataToDatabase = { articleLocalDataSource.insert(it) },
        getDataFromLocalSource = { articleLocalDataSource.loadBySource(sourceId)}
    ).flowOn(Dispatchers.IO)

    fun getTagHeadline(tag: String) = performGetOperation(
        getDataFromRemoteSource = { articleRemoteDataSource.getForMultiTags(listOf(tag)) },
        saveDataToDatabase = { articleLocalDataSource.insert(it) },
        getDataFromLocalSource = { articleLocalDataSource.loadByTags(listOf(tag))}
    ).flowOn(Dispatchers.IO)

}