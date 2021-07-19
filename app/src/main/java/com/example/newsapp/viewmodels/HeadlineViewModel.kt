package com.example.newsapp.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.newsapp.data.models.Article
import com.example.newsapp.data.repositories.HeadlineRepository
import com.example.newsapp.data.repositories.HomeRepository
import com.example.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class HeadlineViewModel @Inject constructor(
    private val headlineRepository: HeadlineRepository
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
            _deleteFlag.value = headlineRepository.deleteByTags(tags)
        }
    }

    init {
        _deleteFlag.value = false
    }

    fun fetchBusiness() {
        businessData = headlineRepository.getBusinessHeadline()
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun fetchTech() {
         techData = headlineRepository.getTechHeadline()
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun fetchStartup() {
        startupData = headlineRepository.getStartupHeadline()
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun fetchScience() {
        scienceData = headlineRepository.getScienceHeadline()
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun fetchLife() {
        lifeData = headlineRepository.getLifeHeadline()
            .asLiveData(viewModelScope.coroutineContext)
    }
}