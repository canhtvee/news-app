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

    fun deleteHeadline(tags: List<String>) {
        viewModelScope.launch {
            headlineRepository.deleteByTags(tags)
        }
    }

    init {
        val _businessData = MutableLiveData<Resource<List<Article>>>()
        businessData = _businessData
    }

    fun fetchBusiness() {
        Log.d("_HeadlineViewModel", "call fetchBusiness")

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