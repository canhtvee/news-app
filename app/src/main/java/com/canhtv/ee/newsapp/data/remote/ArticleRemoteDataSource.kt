package com.canhtv.ee.newsapp.data.remote

import com.canhtv.ee.newsapp.data.models.ApiResponse
import com.canhtv.ee.newsapp.utils.Resource
import javax.inject.Inject

class ArticleRemoteDataSource @Inject constructor(
    private val articleService: ArticleService
): BaseRemoteDataSource() {

    //private val API_KEY: String = "72c24663e06140ae915f4a103374c000"
    private val API_KEY: String = "40c0eda347094331862d35f3bcc45da8"

    suspend fun getFromMultiSources(sources: List<String>): List<Resource<ApiResponse>> {
        val result = mutableListOf<Resource<ApiResponse>>()
        sources.forEach { id ->
            getResult(id) { articleService.getNewsBySource(id, API_KEY) }.also { result.add(it) }
        }
        return result
    }

    suspend fun getForMultiTags(tags: List<String>): List<Resource<ApiResponse>> {
        val result = mutableListOf<Resource<ApiResponse>>()
        tags.forEach { q ->
            getResult(q) { articleService.getNewsByTag(q, API_KEY) }.also { result.add(it) }
        }
        return result
    }
}