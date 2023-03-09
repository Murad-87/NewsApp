package com.example.testapp1.presentation.breakingNewsFragment.ui.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.example.testapp1.data.remote.model.NewArticleRemote

class DiffCallbackArticles : DiffUtil.ItemCallback<NewArticleRemote>() {
    override fun areItemsTheSame(oldItem: NewArticleRemote, newItem: NewArticleRemote) =
        oldItem.link == newItem.link

    override fun areContentsTheSame(oldItem: NewArticleRemote, newItem: NewArticleRemote) =
        oldItem == newItem
}