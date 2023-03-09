package com.example.testapp1.data.remote.api

import com.example.testapp1.data.remote.model.NewsDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("api/1/news")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "ru",
        @Query("apiKey")
        apiKey: String = API_KEY,
        @Query("language")
        language: String = "ru",
        @Query("category")
        category: String = "politics, business",
    ): Response<NewsDataResponse>

    @GET("api/1/news")
    suspend fun searchForNews(
        @Query("qInTitle")
        searchQuery: String,
        @Query("apiKey")
        apiKey: String = API_KEY,
        @Query("language")
        language: String = "ru",
    ): Response<NewsDataResponse>

    companion object {
        const val BASE_URL = "https://newsdata.io"
        private const val API_KEY = "pub_18457600b4ef2837f240c7c4f20dd7dc4aa88"
    }
}