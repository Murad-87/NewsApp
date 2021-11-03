package com.example.testapp1.di.feature.article_fragment

import com.example.testapp1.feature.ui.ArticleFragment
import dagger.Component

@Component(modules = [ArticleFragmentModule::class])
interface ArticleFragmentComponent {

    fun injectArticleFragment(articleFragment: ArticleFragment)
}