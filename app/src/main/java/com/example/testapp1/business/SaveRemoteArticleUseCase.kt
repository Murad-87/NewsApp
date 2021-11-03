package com.example.testapp1.business

import com.example.testapp1.data.remote.model.ArticleRemote
import com.example.testapp1.data.repository.NewsRepository

class SaveRemoteArticleUseCase(private val repository: NewsRepository) {
    suspend fun save(article: ArticleRemote) {
        repository.upsert(article)
    }
}