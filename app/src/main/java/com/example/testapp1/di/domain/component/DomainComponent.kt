package com.example.testapp1.di.domain.component

import com.example.testapp1.domain.BreakingNewsUseCase
import com.example.testapp1.domain.SaveRemoteArticleUseCase
import com.example.testapp1.domain.SavedNewsInteractor
import com.example.testapp1.domain.SearchedNewsUseCase
import com.example.testapp1.di.domain.DomainScope
import com.example.testapp1.di.data.component.DataComponent
import com.example.testapp1.di.domain.module.InteractorModule
import dagger.Component

@Component(dependencies = [DataComponent::class], modules = [InteractorModule::class])
@DomainScope
interface DomainComponent {
    fun provideBreakingNewsUseCase(): BreakingNewsUseCase
    fun provideSavedNewsInteractor(): SavedNewsInteractor
    fun provideSaveRemoteArticleUseCase(): SaveRemoteArticleUseCase
    fun provideSearchedNewsUseCase(): SearchedNewsUseCase
}