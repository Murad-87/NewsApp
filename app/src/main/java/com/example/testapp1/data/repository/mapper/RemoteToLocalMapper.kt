package com.example.testapp1.data.repository.mapper

import com.example.testapp1.data.local.model.ArticleDtoModel
import com.example.testapp1.data.remote.model.NewArticleRemote
import com.example.testapp1.data.utils.Mapper
import javax.inject.Inject

class RemoteToLocalMapper @Inject constructor() : Mapper<NewArticleRemote, ArticleDtoModel> {

    override fun map(input: NewArticleRemote) : ArticleDtoModel =
        ArticleDtoModel(
            title = input.title.orEmpty(),
            content = input.content,
            description = input.description,
            pubDate = input.pubDate,
            link = input.link,
            imageUrl = input.imageUrl,
            sourceId = input.sourceId
    )
}