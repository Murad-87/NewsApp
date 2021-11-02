package com.example.testapp1.data.remote.model


import androidx.annotation.Keep


@Keep
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
)