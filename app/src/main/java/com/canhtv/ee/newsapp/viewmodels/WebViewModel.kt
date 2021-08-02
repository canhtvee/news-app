package com.canhtv.ee.newsapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.canhtv.ee.newsapp.data.models.Article

class WebViewModel : ViewModel(){

    private val _viewData = MutableLiveData<Article>()
    val viewData: LiveData<Article> = _viewData

    fun setViewData(article: Article){
        _viewData.value = article
    }
}