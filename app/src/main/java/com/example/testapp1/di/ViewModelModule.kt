package com.example.testapp1.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapp1.presentation.articleFragment.presentation.ArticleFragmentViewModel
import com.example.testapp1.presentation.breakingNewsFragment.presentation.BreakingNewsViewModel
import com.example.testapp1.presentation.savedNewsFragment.presentation.SavedNewsViewModel
import com.example.testapp1.presentation.searchNewsFragment.presentation.SearchNewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ArticleFragmentViewModel::class)
    fun bindArticleFragmentViewModel(articleFragmentViewModel: ArticleFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BreakingNewsViewModel::class)
    fun bindBreakingNewsViewModel(breakingNewsViewModel: BreakingNewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SavedNewsViewModel::class)
    fun bindSavedNewsViewModel(savedNewsViewModel: SavedNewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchNewsViewModel::class)
    fun bindSearchNewsViewModel(searchNewsViewModel: SearchNewsViewModel): ViewModel
}