package com.example.testapp1.presentation.savedNewsFragment.ui.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.example.testapp1.data.local.model.ArticleDbModel

class DiffCallback : DiffUtil.ItemCallback<ArticleDbModel>() {
    override fun areItemsTheSame(oldItem: ArticleDbModel, newItem: ArticleDbModel) =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: ArticleDbModel, newItem: ArticleDbModel) =
        oldItem == newItem
}