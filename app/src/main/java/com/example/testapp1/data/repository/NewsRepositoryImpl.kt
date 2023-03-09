package com.example.testapp1.data.repository

import com.example.testapp1.data.local.dao.ArticleDao
import com.example.testapp1.data.local.model.ArticleDbModel
import com.example.testapp1.data.remote.api.NewsAPI
import com.example.testapp1.data.remote.model.NewArticleRemote
import com.example.testapp1.data.remote.model.NewsDataResponse
import com.example.testapp1.data.repository.mapper.RemoteToLocalMapper
import com.example.testapp1.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsAPI,
    private val dao: ArticleDao,
    private val mapper: RemoteToLocalMapper
) : NewsRepository {

    override suspend fun getBreakingNews(countryCode: String): Response<NewsDataResponse> =
        api.getBreakingNews(countryCode)

    override suspend fun getSearchNews(searchQuery: String): Response<NewsDataResponse> =
        api.searchForNews(searchQuery)

    override suspend fun upsert(newArticleRemote: NewArticleRemote) {
        newArticleRemote.let(mapper::map)
            .let { dao.upsert(it) }
    }

    override suspend fun reload(article: ArticleDbModel) {
        dao.upsert(article)
    }

    override suspend fun deleteArticle(article: ArticleDbModel) {
        dao.deleteArticle(article)
    }

    override fun flow(): Flow<List<ArticleDbModel>> = dao.flow()
}