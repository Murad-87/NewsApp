package com.example.testapp1.data.repository.mapper

import com.example.testapp1.data.local.model.ArticleDbModel
import com.example.testapp1.data.local.model.ArticleInfo
import com.example.testapp1.data.local.model.SourceLocal
import com.example.testapp1.data.remote.model.ArticleRemote
import javax.inject.Inject

class RemoteToLocalMapper @Inject constructor() {
    fun map(articleRemote: ArticleRemote): ArticleDbModel {
        with(articleRemote) {
            return ArticleDbModel(
                title!!,
                articleInfo = ArticleInfo(
                    author,
                    content,
                    description,
                    publishedAt,
                    url,
                    urlToImage,
                ),
                sourceLocal = SourceLocal(
                    sourceRemote?.id,
                    sourceRemote?.name,
                )
            )
        }
    }
}