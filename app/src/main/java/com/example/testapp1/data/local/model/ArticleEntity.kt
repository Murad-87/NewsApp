package com.example.testapp1.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ArticlesEntity")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @Embedded
    val articleInfo: ArticleInfo,
    @Embedded
    val sourceLocal: SourceLocal?
)