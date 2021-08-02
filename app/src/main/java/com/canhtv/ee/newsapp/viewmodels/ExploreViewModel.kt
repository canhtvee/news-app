package com.canhtv.ee.newsapp.viewmodels

import androidx.lifecycle.*
import com.canhtv.ee.newsapp.data.models.Article
import com.canhtv.ee.newsapp.data.repositories.HomeRepository
import com.canhtv.ee.newsapp.utils.Resource
import com.canhtv.ee.newsapp.utils.SourcePlanning
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
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
        exploreData = homeRepository.getTagsHeadline(sourcePlanning.tagList)
            .asLiveData(viewModelScope.coroutineContext)
    }
}