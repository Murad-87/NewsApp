package com.example.testapp1.di.feature.saved_news_fragment

import com.example.testapp1.feature.ui.SavedNewsFragment
import dagger.Component

@Component(modules = [SavedNewsFragment::class])
interface SavedNewsFragmentComponent {

    fun injectSavedNewsFragment(savedNewsFragment: SavedNewsFragment)
}