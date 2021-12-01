package com.example.testapp1.feature.articleFragment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp1.business.SaveRemoteArticleUseCase
import com.example.testapp1.data.remote.model.ArticleRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleFragmentViewModel @Inject constructor(
    private val saveRemoteArticleUseCase: SaveRemoteArticleUseCase
) : ViewModel() {
    fun save(article: ArticleRemote) {
        viewModelScope.launch(Dispatchers.IO) {
            saveRemoteArticleUseCase.save(article)
        }
    }
}