package com.example.testapp1.data.repository

import com.example.testapp1.data.local.dao.ArticleDao
import com.example.testapp1.data.local.model.ArticleEntity
import com.example.testapp1.data.remote.api.NewsAPI
import com.example.testapp1.data.remote.model.ArticleRemote
import com.example.testapp1.data.remote.model.NewsResponse
import com.example.testapp1.data.repository.mapper.RemoteToLocalMapper
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepository(
    private val api: NewsAPI,
    private val dao: ArticleDao,
    private val mapper: RemoteToLocalMapper,
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse> =
        api.getBreakingNews(countryCode, pageNumber = pageNumber)

    suspend fun getSearchNews(searchQuery: String, pageNumber: Int): Response<NewsResponse> =
        api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(articleRemote: ArticleRemote) {
        articleRemote.let(mapper::map)
            .let { dao.upsert(it) }
    }

    suspend fun reload(article: ArticleEntity) {
        dao.upsert(article)
    }

    suspend fun deleteArticle(article: ArticleEntity) {
        dao.deleteArticle(article)
    }

    fun flow(): Flow<List<ArticleEntity>> = dao.flow()
}