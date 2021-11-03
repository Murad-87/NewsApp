package com.example.testapp1.data.remote.model

import androidx.annotation.Keep

@Keep
data class NewsResponse(
    val articles: List<ArticleRemote>,
    val status: String,
    val totalResults: Int
)