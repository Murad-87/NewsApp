package com.example.testapp1.domain.repository

import com.example.testapp1.data.local.model.ArticleDbModel
import com.example.testapp1.data.remote.model.NewArticleRemote
import com.example.testapp1.data.remote.model.NewsDataResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepository {

    suspend fun getBreakingNews(countryCode: String): Response<NewsDataResponse>

    suspend fun getSearchNews(searchQuery: String): Response<NewsDataResponse>

    suspend fun upsert(newArticleRemote: NewArticleRemote)

    suspend fun reload(article: ArticleDbModel)

    suspend fun deleteArticle(article: ArticleDbModel)

    fun flow(): Flow<List<ArticleDbModel>>
}