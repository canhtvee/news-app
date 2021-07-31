package com.canhtvee.newsapp.di

import com.canhtvee.newsapp.adapters.HeadlineBindingAdapter
import com.canhtvee.newsapp.data.repositories.ExploreTopicRepository
import com.canhtvee.newsapp.data.repositories.HomeRepository
import com.canhtvee.newsapp.utils.SourcePlanning
import com.canhtvee.newsapp.viewmodels.ExploreTopicViewModel
import com.canhtvee.newsapp.viewmodels.ExploreViewModel
import com.canhtvee.newsapp.viewmodels.HeadlineViewModel
import com.canhtvee.newsapp.viewmodels.WebViewModel
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

    @Singleton
    @Provides
    fun provideHeadlineViewModel(
        homeRepository: HomeRepository,
        sourcePlanning: SourcePlanning
    ): HeadlineViewModel = HeadlineViewModel(homeRepository, sourcePlanning)

    @Singleton
    @Provides
    fun provideExploreViewModel(
        homeRepository: HomeRepository,
        sourcePlanning: SourcePlanning
    ): ExploreViewModel = ExploreViewModel(homeRepository, sourcePlanning)

    @Singleton
    @Provides
    fun provideExploreTopicViewModel(exploreTopicRepository: ExploreTopicRepository
    ): ExploreTopicViewModel = ExploreTopicViewModel(exploreTopicRepository)

    @Singleton
    @Provides
    fun provideWebViewModel(): WebViewModel = WebViewModel()

    @Singleton
    @Provides
    fun provideHeadlineBindingAdapterNew(webViewModel: WebViewModel
    ): HeadlineBindingAdapter = HeadlineBindingAdapter(webViewModel)

}