package com.example.testapp1.di.data.module

import android.app.Application
import androidx.room.Room
import com.example.testapp1.data.local.dao.ArticleDao
import com.example.testapp1.data.local.database.ArticleDatabase
import com.example.testapp1.di.data.DataScope
import dagger.Module
import dagger.Provides

@Module
class LocaleModule {

    @Provides
    @DataScope
    fun provideArticleDatabase(context: Application) : ArticleDatabase {
        return Room.databaseBuilder(context, ArticleDatabase::class.java, ArticleDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @DataScope
    fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.getArticleDao()
    }
}