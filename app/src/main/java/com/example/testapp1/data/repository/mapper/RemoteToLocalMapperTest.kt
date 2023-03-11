package com.example.testapp1.data.repository.mapper

import com.example.testapp1.data.local.model.ArticleDtoModel
import com.example.testapp1.data.remote.model.NewArticleRemote

fun NewArticleRemote.toArticleDboModel() = ArticleDtoModel(
    title = title.orEmpty(),
    content = content,
    description = description,
    pubDate = pubDate,
    link = link,
    imageUrl = imageUrl,
    sourceId = sourceId
)