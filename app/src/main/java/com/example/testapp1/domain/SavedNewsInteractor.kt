package com.example.testapp1.domain

import com.example.testapp1.data.local.model.ArticleDtoModel
import com.example.testapp1.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavedNewsInteractor @Inject constructor(
    private val repository: NewsRepository
) {

    fun flow(): Flow<List<ArticleDtoModel>> = repository.flow()

    suspend fun delete(articleEntity: ArticleDtoModel) {
        repository.deleteArticle(articleEntity)
    }

    suspend fun reload(articleEntity: ArticleDtoModel) {
        repository.reload(articleEntity)
    }
}