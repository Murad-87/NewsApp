package com.example.testapp1.data.repository.mapper

import com.example.testapp1.data.local.model.ArticleDbModel
import com.example.testapp1.data.local.model.ArticleInfo
import com.example.testapp1.data.remote.model.NewArticleRemote
import javax.inject.Inject

class RemoteToLocalMapper @Inject constructor() {
    fun map(articleRemote: NewArticleRemote): ArticleDbModel {
        with(articleRemote) {
            return ArticleDbModel(
                title!!,
                articleInfo = ArticleInfo(
                    content,
                    description,
                    pubDate,
                    link,
                    image_url,
                    source_id,
                )
            )
        }
    }
}