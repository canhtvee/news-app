package com.canhtv.ee.newsapp.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @Embedded
    var source      : Source?,
    var author      : String?,
    @PrimaryKey
    var title       : String,
    var description : String,
    var url         : String,
    var urlToImage  : String,
    var publishedAt : String?,
    var content     : String?
)
