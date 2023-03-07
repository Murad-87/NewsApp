package com.example.testapp1.domain

import com.example.testapp1.data.remote.model.NewsResponse
import com.example.testapp1.data.repository.NewsRepositoryImpl
import retrofit2.Response
import javax.inject.Inject

class SearchedNewsUseCase @Inject constructor(private val repository: NewsRepositoryImpl) {
    suspend fun get(searchQuery: String, pageNumber: Int) : Response<NewsResponse> {
        return repository.getSearchNews(searchQuery, pageNumber)
    }
}