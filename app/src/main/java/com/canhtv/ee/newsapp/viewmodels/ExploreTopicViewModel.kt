package com.canhtv.ee.newsapp.viewmodels

import androidx.lifecycle.*
import com.canhtv.ee.newsapp.data.models.Article
import com.canhtv.ee.newsapp.data.repositories.ExploreTopicRepository
import com.canhtv.ee.newsapp.utils.Resource
import javax.inject.Inject

class ExploreTopicViewModel @Inject constructor(
    private val exploreTopicRepository: ExploreTopicRepository
) : ViewModel() {

    lateinit var topicData: LiveData<Resource<List<Article>>>

    var tag: String? = null

    fun fetchSourceHeadline(sourceId: String) {
        topicData = exploreTopicRepository.getSourceHeadline(sourceId).asLiveData(viewModelScope.coroutineContext)
    }

    fun fetTagHeadline(tag: String) {
        topicData = exploreTopicRepository.getTagHeadline(tag).asLiveData(viewModelScope.coroutineContext)
    }

}