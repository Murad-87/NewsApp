package com.example.testapp1.feature.articleFragment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapp1.business.SaveRemoteArticleUseCase

@Suppress("UNCHECKED_CAST")
class ArticleFragmentViewModelFactory(
    private val saveRemoteArticleUseCase: SaveRemoteArticleUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleFragmentViewModel::class.java)) {
            return ArticleFragmentViewModel(
                saveRemoteArticleUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}