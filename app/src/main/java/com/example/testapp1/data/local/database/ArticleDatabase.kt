package com.example.testapp1.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testapp1.data.local.dao.ArticleDao
import com.example.testapp1.data.local.model.ArticleDtoModel

@Database(
    entities = [ArticleDtoModel::class],
    version = 11
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {
        const val DATABASE_NAME = "article_database"
    }
}