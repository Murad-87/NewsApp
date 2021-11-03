package com.example.testapp1.feature.searchNewsFragment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapp1.business.SearchedNewsInteractor

@Suppress("UNCHECKED_CAST")
class SearchNewsViewModelFactory(
    private val searchedNewsInteractor: SearchedNewsInteractor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchNewsViewModel::class.java)) {
            return SearchNewsViewModel(
                searchedNewsInteractor
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}