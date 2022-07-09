package com.example.testapp1.domain

import com.example.testapp1.data.remote.model.ArticleRemote
import com.example.testapp1.data.repository.NewsRepository
import javax.inject.Inject

class SaveRemoteArticleUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend fun save(article: ArticleRemote) {
        repository.upsert(article)
    }
}