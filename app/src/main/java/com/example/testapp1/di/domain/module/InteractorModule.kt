package com.example.testapp1.di.domain.module

import com.example.testapp1.business.BreakingNewsUseCase
import com.example.testapp1.business.SaveRemoteArticleUseCase
import com.example.testapp1.business.SavedNewsInteractor
import com.example.testapp1.business.SearchedNewsUseCase
import com.example.testapp1.data.repository.NewsRepository
import com.example.testapp1.di.domain.DomainScope
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    @DomainScope
    fun provideBreakingNewsUseCase(repository: NewsRepository) : BreakingNewsUseCase {
        return BreakingNewsUseCase(repository)
    }

    @Provides
    @DomainScope
    fun provideSavedNewsInteractor(repository: NewsRepository) : SavedNewsInteractor {
        return SavedNewsInteractor(repository)
    }

    @Provides
    @DomainScope
    fun provideSaveRemoteArticleUseCase(repository: NewsRepository) : SaveRemoteArticleUseCase {
        return SaveRemoteArticleUseCase(repository)
    }

    @Provides
    @DomainScope
    fun provideSearchedNewsUseCase(repository: NewsRepository) : SearchedNewsUseCase {
        return SearchedNewsUseCase(repository)
    }
}