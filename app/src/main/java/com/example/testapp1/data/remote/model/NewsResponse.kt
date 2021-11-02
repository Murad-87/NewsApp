package com.example.testapp1.data.remote.model

import androidx.annotation.Keep
import com.example.testapp1.data.local.model.ArticleEntity

@Keep
data class NewsResponse(
    val articles: MutableList<ArticleEntity>,
    val status: String,
    val totalResults: Int
)