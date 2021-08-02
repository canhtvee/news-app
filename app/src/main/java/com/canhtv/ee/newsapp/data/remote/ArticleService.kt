package com.canhtv.ee.newsapp.data.remote

import com.canhtv.ee.newsapp.data.models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleService {

    @GET("top-headlines")
    suspend fun getNewsBySource(@Query("sources") source: String,
                                @Query("apiKey")  apiKey: String,
    ): Response<ApiResponse>

    @GET("everything")
    suspend fun getNewsByTag(@Query("q") tag: String,
                             @Query("apiKey")  apiKey: String,
    ): Response<ApiResponse>
}