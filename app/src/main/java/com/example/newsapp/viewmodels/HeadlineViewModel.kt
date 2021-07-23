package com.example.newsapp.viewmodels

import androidx.lifecycle.*
import com.example.newsapp.data.models.Article
import com.example.newsapp.data.repositories.HomeRepository
import com.example.newsapp.utils.Resource
import com.example.newsapp.utils.SourcePlanning
import kotlinx.coroutines.launch
import javax.inject.Inject

class HeadlineViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val sourcePlanning: SourcePlanning
) : ViewModel() {

    lateinit var businessData: LiveData<Resource<List<Article>>>
    lateinit var techData    : LiveData<Resource<List<Article>>>
    lateinit var startupData : LiveData<Resource<List<Article>>>
    lateinit var scienceData : LiveData<Resource<List<Article>>>
    lateinit var lifeData    : LiveData<Resource<List<Article>>>

    val _deleteFlag = MutableLiveData<Boolean>()
    val refreshFlag: LiveData<Boolean> = _deleteFlag

    fun deleteHeadline(tags: List<String>) {
        viewModelScope.launch {
            _deleteFlag.value = homeRepository.deleteByTags(tags)
        }
    }

    init {
        _deleteFlag.value = false
    }

    fun fetchBusiness() {
        businessData = homeRepository.getHeadline(sourcePlanning.businessSources)
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun fetchTech() {
         techData = homeRepository.getHeadline(sourcePlanning.techSources)
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun fetchStartup() {
        startupData = homeRepository.getTagsHeadline(sourcePlanning.startup)
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun fetchScience() {
        scienceData = homeRepository.getHeadline(sourcePlanning.scienceSources)
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun fetchLife() {
        lifeData = homeRepository.getHeadline(sourcePlanning.lifeSources)
            .asLiveData(viewModelScope.coroutineContext)
    }
}