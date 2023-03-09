package com.example.testapp1.domain

import com.example.testapp1.data.remote.model.NewArticleRemote
import com.example.testapp1.domain.repository.NewsRepository
import javax.inject.Inject

class SaveRemoteArticleUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    suspend fun save(article: NewArticleRemote) {
        repository.upsert(article)
    }
}