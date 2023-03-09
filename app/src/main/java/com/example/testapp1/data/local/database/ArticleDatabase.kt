package com.example.testapp1.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.testapp1.data.local.dao.ArticleDao
import com.example.testapp1.data.local.model.ArticleDbModel

@Database(
    entities = [ArticleDbModel::class],
    version = 9
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {
        const val DATABASE_NAME = "article_database"
    }
}