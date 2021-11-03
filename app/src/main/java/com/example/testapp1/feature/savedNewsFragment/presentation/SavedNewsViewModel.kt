package com.example.testapp1.feature.savedNewsFragment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp1.business.SavedNewsInteractor
import com.example.testapp1.data.local.model.ArticleEntity
import com.example.testapp1.data.remote.model.ArticleRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedNewsViewModel(private val savedNewsInteractor: SavedNewsInteractor) : ViewModel() {

    fun saveArticle(article: ArticleRemote) {
        viewModelScope.launch(Dispatchers.IO) {
            savedNewsInteractor.save(article)
        }
    }

    fun getSavedNews() = savedNewsInteractor.flow()

    fun deleteArticle(article: ArticleEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            savedNewsInteractor.delete(article)
        }
    }
}