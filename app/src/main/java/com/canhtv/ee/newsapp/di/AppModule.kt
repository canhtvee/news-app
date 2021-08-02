package com.canhtv.ee.newsapp.di

import com.canhtv.ee.newsapp.adapters.HeadlineBindingAdapter
import com.canhtv.ee.newsapp.data.repositories.ExploreTopicRepository
import com.canhtv.ee.newsapp.data.repositories.HomeRepository
import com.canhtv.ee.newsapp.utils.SourcePlanning
import com.canhtv.ee.newsapp.viewmodels.ExploreTopicViewModel
import com.canhtv.ee.newsapp.viewmodels.ExploreViewModel
import com.canhtv.ee.newsapp.viewmodels.HeadlineViewModel
import com.canhtv.ee.newsapp.viewmodels.WebViewModel
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