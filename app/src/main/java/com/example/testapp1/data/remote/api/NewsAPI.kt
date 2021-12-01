package com.example.testapp1.data.remote.api

import com.example.testapp1.data.remote.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "ru",
        @Query("apiKey")
        apiKey: String = API_KEY,
        @Query("page")
        pageNumber: Int = 1,
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    companion object {
        const val BASE_URL = "http://newsapi.org"
        private const val API_KEY = "9b2501d7b1ce481eab539c2ae6a8eb1a"
    }
}