package com.example.testapp1.presentation.breakingNewsFragment.ui.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapp1.data.remote.model.NewArticleRemote
import com.example.testapp1.databinding.ItemArticlePreviewBinding

class NewsAdapter :
    ListAdapter<NewArticleRemote, NewsAdapter.ArticleViewHolder>(DiffCallbackArticles()) {

    private var onItemClickListener: ((NewArticleRemote) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =
            ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnItemClickListener(listener: (NewArticleRemote) -> Unit) {
        onItemClickListener = listener
    }

    inner class ArticleViewHolder(private val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newArticleRemote: NewArticleRemote) {
            with(binding) {
                Glide.with(binding.root).load(newArticleRemote.imageUrl).into(ivArticleImage)
                tvSource.text = newArticleRemote.sourceId
                tvTitle.text = newArticleRemote.title
                tvDescription.text = newArticleRemote.description
                tvPublishedAt.text = newArticleRemote.pubDate
                root.setOnClickListener {
                    onItemClickListener?.let { it(newArticleRemote) }
                }
            }
        }
    }
}