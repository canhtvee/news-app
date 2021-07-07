package com.example.newsapp.viewmodels

import androidx.lifecycle.*
import com.example.newsapp.data.repositories.HomeRepository
import com.example.newsapp.utils.Resource
import com.example.newsapp.data.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    val homeRepository: HomeRepository
) : ViewModel() {

    var data: LiveData<Resource<List<Article>>> = homeRepository.getTagsHeadline()
        .asLiveData(viewModelScope.coroutineContext)

}