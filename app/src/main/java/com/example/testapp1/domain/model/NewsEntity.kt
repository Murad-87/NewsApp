package com.example.testapp1.domain.model

data class NewsEntity(
    val title: String,
    val content: String?,
    val description: String?,
    val pubDate: String?,
    val link: String?,
    val imageUrl: String?,
    val sourceId: String,
)
