package com.example.testapp1.feature.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapp1.data.remote.model.ArticleRemote
import com.example.testapp1.databinding.ItemArticlePreviewBinding

class NewsAdapter :
    ListAdapter<ArticleRemote, NewsAdapter.ArticleViewHolder>(DiffCallbackArticles()) {

    private var onItemClickListener: ((ArticleRemote) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =
            ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnItemClickListener(listener: (ArticleRemote) -> Unit) {
        onItemClickListener = listener
    }

    inner class ArticleViewHolder(private val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(articleRemote: ArticleRemote) {
            with(binding) {
                Glide.with(binding.root).load(articleRemote.urlToImage).into(ivArticleImage)
                tvSource.text = articleRemote.sourceRemote?.name
                tvTitle.text = articleRemote.title
                tvDescription.text = articleRemote.description
                tvPublishedAt.text = articleRemote.publishedAt
                root.setOnClickListener {
                    onItemClickListener?.let { it(articleRemote) }
                }
            }
        }
    }

    class DiffCallbackArticles : DiffUtil.ItemCallback<ArticleRemote>() {
        override fun areItemsTheSame(oldItem: ArticleRemote, newItem: ArticleRemote) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: ArticleRemote, newItem: ArticleRemote) =
            oldItem == newItem
    }
}