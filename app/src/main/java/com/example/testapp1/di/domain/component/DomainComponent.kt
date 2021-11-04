package com.example.testapp1.di.domain.component

import com.example.testapp1.business.BreakingNewsInteractor
import com.example.testapp1.business.SaveRemoteArticleUseCase
import com.example.testapp1.business.SavedNewsInteractor
import com.example.testapp1.business.SearchedNewsInteractor
import com.example.testapp1.di.data.component.DataComponent
import com.example.testapp1.di.domain.module.InteractorModule
import dagger.Component

@Component(dependencies = [DataComponent::class], modules = [InteractorModule::class])
interface DomainComponent {
    fun provideBreakingNewsInteractor(): BreakingNewsInteractor
    fun provideSavedNewsInteractor(): SavedNewsInteractor
    fun provideSaveRemoteArticleUseCase(): SaveRemoteArticleUseCase
    fun provideSearchedNewsInteractor(): SearchedNewsInteractor
}