package com.example.testapp1.di

import com.example.testapp1.data.repository.NewsRepository
import com.example.testapp1.domain.BreakingNewsUseCase
import com.example.testapp1.domain.SaveRemoteArticleUseCase
import com.example.testapp1.domain.SavedNewsInteractor
import com.example.testapp1.domain.SearchedNewsUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideBreakingNewsUseCase(repository: NewsRepository) : BreakingNewsUseCase {
        return BreakingNewsUseCase(repository)
    }

    @Provides
    fun provideSavedNewsInteractor(repository: NewsRepository) : SavedNewsInteractor {
        return SavedNewsInteractor(repository)
    }

    @Provides
    fun provideSaveRemoteArticleUseCase(repository: NewsRepository) : SaveRemoteArticleUseCase {
        return SaveRemoteArticleUseCase(repository)
    }

    @Provides
    fun provideSearchedNewsUseCase(repository: NewsRepository) : SearchedNewsUseCase {
        return SearchedNewsUseCase(repository)
    }
}