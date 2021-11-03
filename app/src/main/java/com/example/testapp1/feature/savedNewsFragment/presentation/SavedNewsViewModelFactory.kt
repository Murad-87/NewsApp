package com.example.testapp1.feature.savedNewsFragment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapp1.business.SaveRemoteArticleUseCase
import com.example.testapp1.business.SavedNewsInteractor

@Suppress("UNCHECKED_CAST")
class SavedNewsViewModelFactory(
    private val savedNewsInteractor: SavedNewsInteractor,
    private val saveRemoteArticleUseCase: SaveRemoteArticleUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedNewsViewModel::class.java)) {
            return SavedNewsViewModel(
                savedNewsInteractor,
                saveRemoteArticleUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}