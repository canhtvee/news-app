package com.canhtv.ee.newsapp.viewmodels

import androidx.lifecycle.*
import com.canhtv.ee.newsapp.data.models.Article
import com.canhtv.ee.newsapp.data.repositories.HomeRepository
import com.canhtv.ee.newsapp.utils.RefreshFlag
import com.canhtv.ee.newsapp.utils.Resource
import com.canhtv.ee.newsapp.utils.SourcePlanning
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

    val refreshFlag = RefreshFlag()

    fun deleteHeadline(flag: MutableLiveData<Boolean>, tags: List<String>) {
        viewModelScope.launch {
            flag.value = homeRepository.deleteByTags(tags)
        }
    }

    init {
        refreshFlag._businessFlag.value = false
        refreshFlag._techFlag.value = false
        refreshFlag._startupFlag.value = false
        refreshFlag._scienceFlag.value = false
        refreshFlag._lifeFlag.value = false

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