package com.example.testapp1.domain.repository

import com.example.testapp1.data.remote.model.NewsResponse
import retrofit2.Response

interface NewsRepository {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse>

    suspend fun getSearchNews(searchQuery: String, pageNumber: Int): Response<NewsResponse>
}