package com.example.testapp1.di.search_news_fragment

import com.example.testapp1.feature.ui.SearchNewsFragment
import dagger.Component

@Component(modules = [SearchNewsFragment::class])
interface SearchNewsFragmentComponent {

    fun injectSearchNewsFragment(searchNewsFragment: SearchNewsFragment)
}