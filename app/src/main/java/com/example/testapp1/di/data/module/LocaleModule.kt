package com.example.testapp1.di.data.module

import android.app.Application
import androidx.room.Room
import com.example.testapp1.data.local.dao.ArticleDao
import com.example.testapp1.data.local.database.ArticleDatabase
import dagger.Module
import dagger.Provides

@Module
object LocaleModule {

    @Provides
    @JvmStatic
    fun provideArticleDatabase(context: Application) : ArticleDatabase {
        return Room.databaseBuilder(context, ArticleDatabase::class.java, "article_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @JvmStatic
    fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.getArticleDao()
    }
}