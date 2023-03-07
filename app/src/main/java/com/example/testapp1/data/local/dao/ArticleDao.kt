package com.example.testapp1.data.local.dao

import androidx.room.*
import com.example.testapp1.data.local.model.ArticleDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM ArticlesDbModel")
    fun flow(): Flow<List<ArticleDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: ArticleDbModel)

    @Delete
    suspend fun deleteArticle(article: ArticleDbModel)
}