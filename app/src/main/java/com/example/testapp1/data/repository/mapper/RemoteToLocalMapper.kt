package com.example.testapp1.data.repository.mapper

import com.example.testapp1.data.local.model.ArticleEntity
import com.example.testapp1.data.local.model.ArticleInfo
import com.example.testapp1.data.local.model.SourceLocal
import com.example.testapp1.data.remote.model.ArticleRemote
import javax.inject.Inject

class RemoteToLocalMapper {
    fun map(articleRemote: ArticleRemote): ArticleEntity {
        with(articleRemote) {
            return ArticleEntity(
                id,
                ArticleInfo(
                    author,
                    content,
                    description,
                    publishedAt,
                    title,
                    url,
                    urlToImage,
                ),
                SourceLocal(
                    sourceRemote!!.id,
                    sourceRemote.name,
                )
            )
        }
    }
}