package com.canhtvee.newsapp.di

import android.content.Context
import com.canhtvee.newsapp.data.local.AppDatabase
import com.canhtvee.newsapp.data.local.ArticleDao
import com.canhtvee.newsapp.data.remote.ArticleRemoteDataSource
import com.canhtvee.newsapp.data.repositories.HomeRepository
import com.canhtvee.newsapp.utils.SourcePlanning
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = AppDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideArticleDao(db: AppDatabase) = db.articleDao()

    @Singleton
    @Provides
    fun provideHomeRepository(
        articleRemoteDataSource: ArticleRemoteDataSource,
        articleLocalDataSource: ArticleDao,
    ) = HomeRepository(articleRemoteDataSource, articleLocalDataSource)
}