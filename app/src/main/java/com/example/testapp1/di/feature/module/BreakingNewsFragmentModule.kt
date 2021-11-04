package com.example.testapp1.di.feature.module

import androidx.lifecycle.ViewModelProvider
import com.example.testapp1.business.BreakingNewsInteractor
import com.example.testapp1.feature.breakingNewsFragment.presentation.BreakingNewsViewModel
import com.example.testapp1.feature.breakingNewsFragment.presentation.BreakingNewsViewModelFactory
import com.example.testapp1.feature.breakingNewsFragment.ui.BreakingNewsFragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BreakingNewsFragmentModule(private val breakingNewsFragment: BreakingNewsFragment) {

    @Provides
    @Singleton
    fun provideBreakingNewsViewModelFactory(breakingNewsInteractor: BreakingNewsInteractor): BreakingNewsViewModelFactory {
        return BreakingNewsViewModelFactory(breakingNewsInteractor)
    }

    @Provides
    @Singleton
    fun provideBreakingNewsViewModel(breakingNewsViewModelFactory: BreakingNewsViewModelFactory) : BreakingNewsViewModel {
        return ViewModelProvider(
            breakingNewsFragment,
            breakingNewsViewModelFactory
        ).get(BreakingNewsViewModel::class.java)
    }
}
