package com.example.testapp1.domain

import com.example.testapp1.data.remote.model.NewsDataResponse
import com.example.testapp1.domain.repository.NewsRepository
import javax.inject.Inject

class BreakingNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    suspend fun get(countryCode: String): NewsDataResponse {
        return repository.getBreakingNews(countryCode)
    }
}