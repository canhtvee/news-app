package com.canhtv.ee.newsapp.di

import android.content.Context
import com.canhtv.ee.newsapp.data.local.AppDatabase
import com.canhtv.ee.newsapp.data.local.ArticleDao
import com.canhtv.ee.newsapp.data.remote.ArticleRemoteDataSource
import com.canhtv.ee.newsapp.data.repositories.HomeRepository
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