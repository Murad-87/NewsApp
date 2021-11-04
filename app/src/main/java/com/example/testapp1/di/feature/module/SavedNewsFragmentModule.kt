package com.example.testapp1.di.feature.module

import androidx.lifecycle.ViewModelProvider
import com.example.testapp1.business.SavedNewsInteractor
import com.example.testapp1.feature.savedNewsFragment.presentation.SavedNewsViewModel
import com.example.testapp1.feature.savedNewsFragment.presentation.SavedNewsViewModelFactory
import com.example.testapp1.feature.savedNewsFragment.ui.SavedNewsFragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SavedNewsFragmentModule(private val savedNewsFragment: SavedNewsFragment) {

    @Provides
    @Singleton
    fun provideSavedNewsViewModelFactory(savedNewsInteractor: SavedNewsInteractor): SavedNewsViewModelFactory {
        return SavedNewsViewModelFactory(savedNewsInteractor)
    }

    @Provides
    @Singleton
    fun provideSavedNewsViewModel(savedNewsViewModelFactory: SavedNewsViewModelFactory) : SavedNewsViewModel {
        return ViewModelProvider(
            savedNewsFragment,
            savedNewsViewModelFactory
        ).get(SavedNewsViewModel::class.java)
    }
}