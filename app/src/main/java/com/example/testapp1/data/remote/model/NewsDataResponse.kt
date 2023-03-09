package com.example.testapp1.data.remote.model

data class NewsDataResponse(
    val nextPage: Any,
    val results: ArrayList<NewArticleRemote>,
    val status: String,
    val totalResults: Int
)