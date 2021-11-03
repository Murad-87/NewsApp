package com.example.testapp1.data.repository

import com.example.testapp1.data.local.dao.ArticleDao
import com.example.testapp1.data.local.model.ArticleEntity
import com.example.testapp1.data.remote.api.NewsAPI
import com.example.testapp1.data.remote.api.RetrofitInstance
import com.example.testapp1.data.repository.mapper.RemoteToLocalMapper
import kotlinx.coroutines.flow.Flow

class NewsRepository(
    private val api: NewsAPI,
    private val dao: ArticleDao,
    private val mapper: RemoteToLocalMapper,
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(articles: List<ArticleEntity>) = dao.upsert(articles)

    suspend fun deleteArticle(article: ArticleEntity) = dao.deleteArticle(article)


    fun flow(): Flow<List<ArticleEntity>> = dao.flow()
}