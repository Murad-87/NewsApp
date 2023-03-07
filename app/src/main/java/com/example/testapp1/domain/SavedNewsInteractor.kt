package com.example.testapp1.domain

import com.example.testapp1.data.local.model.ArticleDbModel
import com.example.testapp1.data.repository.NewsRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavedNewsInteractor @Inject constructor(private val repository: NewsRepositoryImpl) {

    fun flow(): Flow<List<ArticleDbModel>> = repository.flow()

    suspend fun delete(articleEntity: ArticleDbModel) {
        repository.deleteArticle(articleEntity)
    }

    suspend fun reload(articleEntity: ArticleDbModel) {
        repository.reload(articleEntity)
    }
}