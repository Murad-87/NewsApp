package com.example.testapp1.data.local.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "ArticlesEntity")
data class ArticleEntity(
    @PrimaryKey
    val title: String,
    @Embedded
    val articleInfo: ArticleInfo,
    @Embedded
    val sourceLocal: SourceLocal?
) : Parcelable