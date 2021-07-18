package com.example.newsapp.data.remote

import android.util.Log
import com.example.newsapp.data.models.ApiResponse
import com.example.newsapp.utils.Resource
import javax.inject.Inject

class ArticleRemoteDataSource @Inject constructor(
    private val articleService: ArticleService
): BaseRemoteDataSource() {

    private val API_KEY: String = "72c24663e06140ae915f4a103374c000"
    //private val API_KEY: String = "40c0eda347094331862d35f3bcc45da8"

    suspend fun getArticleBySource(source: String) = getResult {
        articleService.getNewsBySource(source, API_KEY) }

    suspend fun getFromMultiSources(sources: List<String>): List<Resource<ApiResponse>> {
        Log.d("_ArticlRemoteDataSource", "call getFromMultiSources")
        val result = mutableListOf<Resource<ApiResponse>>()
        sources.forEach { id ->
            getResult2(id) { articleService.getNewsBySource(id, API_KEY) }.also { result.add(it) }
        }
        return result
    }

    suspend fun getForMultiTags(tags: List<String>): List<Resource<ApiResponse>> {
        val result = mutableListOf<Resource<ApiResponse>>()
        tags.forEach { q ->
            getResult2(q) { articleService.getNewsByTag(q, API_KEY) }.also { result.add(it) }
        }
        return result
    }
}