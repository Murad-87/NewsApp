package com.example.testapp1.data.local.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleInfo(
    val content: String?,
    val description: String?,
    val pubDate: String?,
    val link: String?,
    val image_url: String?,
    val source_id: String,
): Parcelable