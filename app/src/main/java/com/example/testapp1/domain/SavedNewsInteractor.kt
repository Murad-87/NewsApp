package com.example.testapp1.domain

import com.example.testapp1.data.local.model.ArticleEntity
import com.example.testapp1.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SavedNewsInteractor(private val repository: NewsRepository) {

    fun flow(): Flow<List<ArticleEntity>> = repository.flow()

    suspend fun delete(articleEntity: ArticleEntity) {
        repository.deleteArticle(articleEntity)
    }

    suspend fun reload(articleEntity: ArticleEntity) {
        repository.reload(articleEntity)
    }
}