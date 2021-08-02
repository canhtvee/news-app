package com.canhtv.ee.newsapp.data.models

data class ApiResponse(
    var status: String,
    var totalResults: String,
    var articles: MutableList<Article>
)