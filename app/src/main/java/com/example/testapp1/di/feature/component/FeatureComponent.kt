package com.example.testapp1.di.feature.component

import com.example.testapp1.di.feature.FeatureScope
import com.example.testapp1.di.domain.component.DomainComponent
import com.example.testapp1.di.feature.module.ViewModelModule
import com.example.testapp1.presentation.articleFragment.ui.ArticleFragment
import com.example.testapp1.presentation.breakingNewsFragment.ui.BreakingNewsFragment
import com.example.testapp1.presentation.savedNewsFragment.ui.SavedNewsFragment
import com.example.testapp1.presentation.searchNewsFragment.ui.SearchNewsFragment
import dagger.Component

@Component(
    dependencies = [DomainComponent::class],
    modules = [
        ViewModelModule::class
    ]
)
@FeatureScope
interface FeatureComponent {
    fun inject(articleFragment: ArticleFragment)
    fun inject(breakingNewsFragment: BreakingNewsFragment)
    fun inject(savedNewsFragment: SavedNewsFragment)
    fun inject(searchNewsFragment: SearchNewsFragment)
}