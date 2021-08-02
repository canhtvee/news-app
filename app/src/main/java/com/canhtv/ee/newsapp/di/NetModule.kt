package com.canhtv.ee.newsapp.di

import com.canhtv.ee.newsapp.data.remote.ArticleRemoteDataSource
import com.canhtv.ee.newsapp.data.remote.ArticleService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetModule {

    private const val BASE_URL: String = "https://newsapi.org/v2/"

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    fun provideArticleService(retrofit: Retrofit): ArticleService = retrofit.create(ArticleService::class.java)

    @Singleton
    @Provides
    fun provideArticleRemoteDataSource(articleService: ArticleService) = ArticleRemoteDataSource(articleService)

}