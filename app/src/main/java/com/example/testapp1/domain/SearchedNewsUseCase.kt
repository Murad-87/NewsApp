package com.example.testapp1.domain

import com.example.testapp1.data.remote.model.NewsDataResponse
import com.example.testapp1.domain.repository.NewsRepository
import retrofit2.Response
import javax.inject.Inject

class SearchedNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    suspend fun get(searchQuery: String): Response<NewsDataResponse> {
        return repository.getSearchNews(searchQuery)
    }
}