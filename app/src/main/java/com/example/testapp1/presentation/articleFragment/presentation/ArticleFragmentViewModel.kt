package com.example.testapp1.presentation.articleFragment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp1.domain.SaveRemoteArticleUseCase
import com.example.testapp1.data.remote.model.NewArticleRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleFragmentViewModel @Inject constructor(
    private val saveRemoteArticleUseCase: SaveRemoteArticleUseCase
) : ViewModel() {
    fun save(article: NewArticleRemote) {
        viewModelScope.launch(Dispatchers.IO) {
            saveRemoteArticleUseCase.save(article)
        }
    }
}