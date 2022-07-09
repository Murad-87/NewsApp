package com.example.testapp1.di

import android.app.Application
import com.example.testapp1.presentation.articleFragment.ui.ArticleFragment
import com.example.testapp1.presentation.breakingNewsFragment.ui.BreakingNewsFragment
import com.example.testapp1.presentation.savedNewsFragment.ui.SavedNewsFragment
import com.example.testapp1.presentation.searchNewsFragment.ui.SearchNewsFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, DomainModule::class, RemoteModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun applicationContext(): Application

    fun inject(breakingNewsFragment: BreakingNewsFragment)

    fun inject(savedNewsFragment: SavedNewsFragment)

    fun inject(searchNewsFragment: SearchNewsFragment)

    fun inject(articleFragment: ArticleFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}