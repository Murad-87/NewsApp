package com.example.testapp1.feature.savedNewsFragment.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapp1.data.local.model.ArticleEntity
import com.example.testapp1.databinding.ItemArticlePreviewBinding

class SavedNewsAdapter :
    ListAdapter<ArticleEntity, SavedNewsAdapter.SavedArticleViewHolder>(DiffCallback()) {

    private var onItemClickListener: ((ArticleEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedArticleViewHolder {
        val binding =
            ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holderSaved: SavedArticleViewHolder, position: Int) {
        holderSaved.bind(getItem(position))
    }

    fun setOnItemClickListener(listener: (ArticleEntity) -> Unit) {
        onItemClickListener = listener
    }

    inner class SavedArticleViewHolder(private val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(articleEntity: ArticleEntity) {
            with(binding) {
                Glide.with(binding.root).load(articleEntity.articleInfo.urlToImage)
                    .into(ivArticleImage)
                tvSource.text = articleEntity.sourceLocal?.name
                tvTitle.text = articleEntity.title
                tvDescription.text = articleEntity.articleInfo.description
                tvPublishedAt.text = articleEntity.articleInfo.publishedAt
                root.setOnClickListener {
                    onItemClickListener?.let { it(articleEntity) }
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ArticleEntity>() {
        override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity) =
            oldItem.title == newItem.title


        override fun areContentsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity) =
            oldItem == newItem
    }
}