package com.example.testapp1.data.repository

import com.example.testapp1.data.local.dao.ArticleDao
import com.example.testapp1.data.remote.api.RetrofitInstance
import com.example.testapp1.data.local.database.ArticleDatabase
import com.example.testapp1.data.local.model.ArticleEntity
import com.example.testapp1.data.remote.api.NewsAPI
import com.example.testapp1.data.repository.mapper.RemoteToLocalModelMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository (

    private val api: NewsAPI,
    private val dao: ArticleDao,
    private val mapper: RemoteToLocalModelMapper,
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: ArticleEntity) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().flow()

    suspend fun deleteArticle(article: ArticleEntity) = db.getArticleDao().deleteArticle(article)

    fun flow(): Flow<List<ArticleEntity>> = dao.flow()
}