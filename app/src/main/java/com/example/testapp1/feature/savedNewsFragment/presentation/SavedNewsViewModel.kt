package com.example.testapp1.feature.savedNewsFragment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.testapp1.business.SavedNewsInteractor
import com.example.testapp1.data.local.model.ArticleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SavedNewsViewModel @Inject constructor(
    private val savedNewsInteractor: SavedNewsInteractor,
) : ViewModel() {

    fun reloadArticle(article: ArticleEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            savedNewsInteractor.reload(article)
        }
    }

    fun getSavedNews() = savedNewsInteractor.flow().asLiveData()

    fun deleteArticle(article: ArticleEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            savedNewsInteractor.delete(article)
        }
    }
}