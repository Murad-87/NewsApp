package com.example.testapp1.data.repository

import com.example.testapp1.data.local.dao.ArticleDao
import com.example.testapp1.data.local.model.ArticleDtoModel
import com.example.testapp1.data.remote.api.NewsAPI
import com.example.testapp1.data.remote.model.NewArticleRemote
import com.example.testapp1.data.remote.model.NewsDataResponse
import com.example.testapp1.data.repository.mapper.RemoteToLocalMapper
import com.example.testapp1.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsAPI,
    private val dao: ArticleDao,
    private val mapper: RemoteToLocalMapper
) : NewsRepository {

    override suspend fun getBreakingNews(countryCode: String): NewsDataResponse =
        api.getBreakingNews(countryCode)

    override suspend fun getSearchNews(searchQuery: String): NewsDataResponse =
        api.searchForNews(searchQuery)

    override suspend fun upsert(newArticleRemote: NewArticleRemote) {
        newArticleRemote.let(mapper::map)
            .let { dao.upsert(it) }
    }

    override suspend fun reload(article: ArticleDtoModel) {
        dao.upsert(article)
    }

    override suspend fun deleteArticle(article: ArticleDtoModel) {
        dao.deleteArticle(article)
    }

    override fun flow(): Flow<List<ArticleDtoModel>> = dao.flow()
}