package com.example.testapp1.data.local.dao

import androidx.room.*
import com.example.testapp1.data.local.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM ArticlesEntity")
    fun flow(): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: ArticleEntity)

    @Delete
    suspend fun deleteArticle(article: ArticleEntity)
}