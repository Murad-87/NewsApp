package com.example.testapp1.data.local.dao

import androidx.room.*
import com.example.testapp1.data.local.model.ArticleDtoModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM ArticlesDbModel")
    fun flow(): Flow<List<ArticleDtoModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: ArticleDtoModel)

    @Delete
    suspend fun deleteArticle(article: ArticleDtoModel)
}