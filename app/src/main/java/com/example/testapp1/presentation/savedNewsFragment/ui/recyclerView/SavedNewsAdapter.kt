package com.example.testapp1.presentation.savedNewsFragment.ui.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapp1.data.local.model.ArticleDtoModel
import com.example.testapp1.databinding.ItemArticlePreviewBinding

class SavedNewsAdapter :
    ListAdapter<ArticleDtoModel, SavedNewsAdapter.SavedArticleViewHolder>(DiffCallback()) {

    private var onItemClickListener: ((ArticleDtoModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedArticleViewHolder {
        val binding =
            ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holderSaved: SavedArticleViewHolder, position: Int) {
        holderSaved.bind(getItem(position))
    }

    fun setOnItemClickListener(listener: (ArticleDtoModel) -> Unit) {
        onItemClickListener = listener
    }

    inner class SavedArticleViewHolder(private val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(articleEntity: ArticleDtoModel) {
            with(binding) {
                Glide.with(binding.root).load(articleEntity.imageUrl)
                    .into(ivArticleImage)
                tvTitle.text = articleEntity.title
                tvDescription.text = articleEntity.description
                tvPublishedAt.text = articleEntity.pubDate
                root.setOnClickListener {
                    onItemClickListener?.let { it(articleEntity) }
                }
            }
        }
    }
}