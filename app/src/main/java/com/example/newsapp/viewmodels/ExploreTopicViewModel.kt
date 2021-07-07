package com.example.newsapp.viewmodels

import androidx.lifecycle.*
import com.example.newsapp.data.models.Article
import com.example.newsapp.data.repositories.HomeRepository
import javax.inject.Inject

class ExploreTopicViewModel @Inject constructor(
    val homeRepository: HomeRepository
) : ViewModel() {
    private val _topicData = MutableLiveData<List<Article>>()
    var topicData: LiveData<List<Article>> = _topicData

    fun setTopicData(articles: List<Article>){
        _topicData.value = articles
    }
    fun setTopicDataBySource(sourceId: String){
        topicData = homeRepository.loadBySource(sourceId).asLiveData(viewModelScope.coroutineContext)
    }
}