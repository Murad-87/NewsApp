package com.example.testapp1.di.data.module

import android.content.Context
import androidx.room.Room
import com.example.testapp1.data.local.dao.ArticleDao
import com.example.testapp1.data.local.database.ArticleDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocaleModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext() : Context {
        return context
    }

    @Provides
    @Singleton
    fun provideArticleDatabase(context: Context) : ArticleDatabase {
        return Room.databaseBuilder(context, ArticleDatabase::class.java, "article_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.getArticleDao()
    }
}