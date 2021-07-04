package com.example.newsapp.di

import androidx.navigation.Navigation
import com.example.newsapp.MainActivity
import com.example.newsapp.R
import com.example.newsapp.adapters.ImageBindingAdapter
import com.example.newsapp.data.repositories.HeadlineRepository
import com.example.newsapp.data.repositories.HomeRepository
import com.example.newsapp.utils.SourcePlanning
import com.example.newsapp.viewmodels.ExploreViewModel
import com.example.newsapp.viewmodels.HeadlineViewModel
import com.example.newsapp.viewmodels.WebViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSourcePlanning(): SourcePlanning = SourcePlanning()

    @Provides
    fun provideImageBindingAdapter(): ImageBindingAdapter = ImageBindingAdapter()

    @Singleton
    @Provides
    fun provideHeadlineViewModel(headlineRepository: HeadlineRepository
    ): HeadlineViewModel = HeadlineViewModel(headlineRepository)

    @Singleton
    @Provides
    fun provideWebViewModel(): WebViewModel = WebViewModel()

    @Singleton
    @Provides
    fun provideExploreViewModel(homeRepository: HomeRepository
    ): ExploreViewModel = ExploreViewModel(homeRepository)

}