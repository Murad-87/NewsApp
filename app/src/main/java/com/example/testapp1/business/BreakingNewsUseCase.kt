package com.example.testapp1.business

import com.example.testapp1.data.remote.model.NewsResponse
import com.example.testapp1.data.repository.NewsRepository
import retrofit2.Response

class BreakingNewsUseCase(private val repository: NewsRepository) {
    suspend fun get(countryCode: String, pageNumber: Int) : Response<NewsResponse> {
        return repository.getBreakingNews(countryCode, pageNumber)
    }
}