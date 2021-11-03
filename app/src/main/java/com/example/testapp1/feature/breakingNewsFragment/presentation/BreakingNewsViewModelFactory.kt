package com.example.testapp1.feature.breakingNewsFragment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapp1.business.BreakingNewsInteractor

@Suppress("UNCHECKED_CAST")
class BreakingNewsViewModelFactory(
    private val breakingNewsInteractor: BreakingNewsInteractor,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BreakingNewsViewModel::class.java)) {
            return BreakingNewsViewModel(
                breakingNewsInteractor
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}