package com.example.testapp1.di.data.module

import com.example.testapp1.data.local.dao.ArticleDao
import com.example.testapp1.data.remote.api.NewsAPI
import com.example.testapp1.data.repository.NewsRepository
import com.example.testapp1.data.repository.mapper.RemoteToLocalMapper
import com.example.testapp1.di.data.DataScope
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @DataScope
    fun provideRemoteToLocalMapper() : RemoteToLocalMapper = RemoteToLocalMapper()

    @Provides
    @DataScope
    fun provideRepository(
        api: NewsAPI,
        dao: ArticleDao,
        mapper: RemoteToLocalMapper
    ): NewsRepository {
        return NewsRepository(api, dao, mapper)
    }
}