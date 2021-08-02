package com.canhtv.ee.newsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.canhtv.ee.newsapp.data.models.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(articles: List<Article>?)

    @Query("SELECT * FROM articles" )
    fun loadAll(): Flow<List<Article>>

    @Query("SELECT * FROM articles WHERE id = :source")
    fun loadBySource(source: String): Flow<List<Article>>

    @Query("SELECT * FROM articles WHERE id IN (:sources)")
    fun loadByMultiSources(sources: List<String>): Flow<List<Article>>

    @Query("SELECT * FROM articles WHERE content IN (:tags)")
    fun loadByTags(tags: List<String>): Flow<List<Article>>

    @Query("DELETE FROM articles")
    suspend fun deleteAll()

    @Query("DELETE FROM articles WHERE content IN (:tags)")
    suspend fun deleteByTags(tags: List<String>)

}