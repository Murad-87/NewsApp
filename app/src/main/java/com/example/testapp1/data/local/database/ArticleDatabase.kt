package com.example.testapp1.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapp1.data.local.dao.ArticleDao
import com.example.testapp1.data.local.model.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1
)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {
        const val DATABASE_NAME = "article_database"
    }
}