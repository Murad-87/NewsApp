package com.example.testapp1.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "ArticlesDbModel")
data class ArticleDtoModel(
    @PrimaryKey
    val title: String,
    val content: String?,
    val description: String?,
    val pubDate: String?,
    val link: String?,
    val imageUrl: String?,
    val sourceId: String,
) : Parcelable