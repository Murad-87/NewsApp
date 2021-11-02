package com.example.testapp1.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testapp1.data.remote.model.SourceRemote

@Entity(tableName = "ArticlesEntity")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @Embedded
    val articleLocal: ArticleLocal,
    @Embedded
    val sourceRemote: SourceRemote?
)