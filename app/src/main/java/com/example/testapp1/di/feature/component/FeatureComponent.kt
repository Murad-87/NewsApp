package com.example.testapp1.di.feature.component

import com.example.testapp1.di.domain.component.DomainComponent
import com.example.testapp1.di.feature.module.ArticleFragmentModule
import com.example.testapp1.di.feature.module.BreakingNewsFragmentModule
import com.example.testapp1.di.feature.module.SavedNewsFragmentModule
import com.example.testapp1.di.feature.module.SearchNewsFragmentModule
import com.example.testapp1.feature.articleFragment.ui.ArticleFragment
import com.example.testapp1.feature.breakingNewsFragment.ui.BreakingNewsFragment
import com.example.testapp1.feature.savedNewsFragment.ui.SavedNewsFragment
import com.example.testapp1.feature.searchNewsFragment.ui.SearchNewsFragment
import dagger.Component

@Component(
    dependencies = [DomainComponent::class], modules = [ArticleFragmentModule::class,
        BreakingNewsFragmentModule::class, SavedNewsFragmentModule::class, SearchNewsFragmentModule::class]
)
interface FeatureComponent {
    fun inject(articleFragment: ArticleFragment)
    fun inject(breakingNewsFragment: BreakingNewsFragment)
    fun inject(savedNewsFragment: SavedNewsFragment)
    fun inject(searchNewsFragment: SearchNewsFragment)
}