package com.example.testapp1.presentation.savedNewsFragment.ui.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.example.testapp1.data.local.model.ArticleDtoModel

class DiffCallback : DiffUtil.ItemCallback<ArticleDtoModel>() {
    override fun areItemsTheSame(oldItem: ArticleDtoModel, newItem: ArticleDtoModel) =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: ArticleDtoModel, newItem: ArticleDtoModel) =
        oldItem == newItem
}