package com.example.testapp1.data.remote.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class ArticleRemote(
    var id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val sourceRemote: SourceRemote?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
): Parcelable