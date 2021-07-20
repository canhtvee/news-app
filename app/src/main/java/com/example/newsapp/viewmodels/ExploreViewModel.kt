package com.example.newsapp.viewmodels

import androidx.lifecycle.*
import com.example.newsapp.data.repositories.HomeRepository
import com.example.newsapp.utils.Resource
import com.example.newsapp.data.models.Article
import com.example.newsapp.utils.SourcePlanning
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    val homeRepository: HomeRepository,
    val sourcePlanning: SourcePlanning
) : ViewModel() {

    lateinit var exploreData: LiveData<Resource<List<Article>>>
    val _deleteFlag = MutableLiveData<Boolean>()
    val refreshFlag: LiveData<Boolean> = _deleteFlag

    fun deleteExplore() {
        viewModelScope.launch {
            _deleteFlag.value = homeRepository.deleteByTags(sourcePlanning.tagList)
        }
    }

    init {
        _deleteFlag.value = false
    }

    fun fetchExplore() {
        exploreData = homeRepository.getTagsHeadline()
            .asLiveData(viewModelScope.coroutineContext)
    }
}