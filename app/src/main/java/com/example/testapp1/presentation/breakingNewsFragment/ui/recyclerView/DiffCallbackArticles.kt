package com.example.testapp1.presentation.breakingNewsFragment.ui.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.example.testapp1.data.remote.model.ArticleRemote

class DiffCallbackArticles : DiffUtil.ItemCallback<ArticleRemote>() {
    override fun areItemsTheSame(oldItem: ArticleRemote, newItem: ArticleRemote) =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: ArticleRemote, newItem: ArticleRemote) =
        oldItem == newItem
}