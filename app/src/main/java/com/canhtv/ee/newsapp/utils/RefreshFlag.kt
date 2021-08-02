package com.canhtv.ee.newsapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class RefreshFlag {

    val _businessFlag = MutableLiveData<Boolean>()
    val businessFlag: LiveData<Boolean> = _businessFlag

    val _techFlag = MutableLiveData<Boolean>()
    val techFlag: LiveData<Boolean> = _techFlag

    val _startupFlag = MutableLiveData<Boolean>()
    val startupFlag: LiveData<Boolean> = _startupFlag

    val _scienceFlag = MutableLiveData<Boolean>()
    val scienceFlag: LiveData<Boolean> = _scienceFlag

    val _lifeFlag = MutableLiveData<Boolean>()
    val lifeFlag: LiveData<Boolean> = _lifeFlag

}