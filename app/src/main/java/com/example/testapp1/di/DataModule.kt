package com.example.testapp1.di

import android.app.Application
import androidx.room.Room
import com.example.testapp1.data.local.dao.ArticleDao
import com.example.testapp1.data.local.database.ArticleDatabase
import com.example.testapp1.data.remote.api.NewsAPI
import com.example.testapp1.data.repository.NewsRepositoryImpl
import com.example.testapp1.data.repository.mapper.RemoteToLocalMapper
import com.example.testapp1.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides


@Module
class DataModule {

    @Provides
    @ApplicationScope
    fun provideRemoteToLocalMapper() : RemoteToLocalMapper = RemoteToLocalMapper()

    @Provides
    @ApplicationScope
    fun provideArticleDatabase(context: Application) : ArticleDatabase {
        return Room.databaseBuilder(context, ArticleDatabase::class.java, ArticleDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.getArticleDao()
    }

    @Provides
    @ApplicationScope
    fun provideRepository(
        api: NewsAPI,
        dao: ArticleDao,
        mapper: RemoteToLocalMapper
    ): NewsRepository {
        return NewsRepositoryImpl(api, dao, mapper)
    }
}