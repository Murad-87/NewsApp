package com.example.testapp1.feature.savedNewsFragment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.testapp1.business.SaveRemoteArticleUseCase
import com.example.testapp1.business.SavedNewsInteractor
import com.example.testapp1.data.local.model.ArticleEntity
import com.example.testapp1.data.remote.model.ArticleRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedNewsViewModel(
    private val savedNewsInteractor: SavedNewsInteractor,
    private val saveRemoteArticleUseCase: SaveRemoteArticleUseCase
) : ViewModel() {

    fun saveArticle(article: ArticleRemote) {
        viewModelScope.launch(Dispatchers.IO) {
            saveRemoteArticleUseCase.save(article)
        }
    }

    fun getSavedNews() = savedNewsInteractor.flow().asLiveData()

    fun deleteArticle(article: ArticleEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            savedNewsInteractor.delete(article)
        }
    }
}