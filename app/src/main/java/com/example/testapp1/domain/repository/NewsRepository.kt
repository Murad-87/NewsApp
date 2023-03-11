package com.example.testapp1.domain.repository

import com.example.testapp1.data.local.model.ArticleDtoModel
import com.example.testapp1.data.remote.model.NewArticleRemote
import com.example.testapp1.data.remote.model.NewsDataResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getBreakingNews(countryCode: String): NewsDataResponse

    suspend fun getSearchNews(searchQuery: String): NewsDataResponse

    suspend fun upsert(newArticleRemote: NewArticleRemote)

    suspend fun reload(article: ArticleDtoModel)

    suspend fun deleteArticle(article: ArticleDtoModel)

    fun flow(): Flow<List<ArticleDtoModel>>
}