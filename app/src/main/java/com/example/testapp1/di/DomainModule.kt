package com.example.testapp1.di

import com.example.testapp1.data.repository.NewsRepositoryImpl
import com.example.testapp1.domain.BreakingNewsUseCase
import com.example.testapp1.domain.SaveRemoteArticleUseCase
import com.example.testapp1.domain.SavedNewsInteractor
import com.example.testapp1.domain.SearchedNewsUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideBreakingNewsUseCase(repository: NewsRepositoryImpl) : BreakingNewsUseCase {
        return BreakingNewsUseCase(repository)
    }

    @Provides
    fun provideSavedNewsInteractor(repository: NewsRepositoryImpl) : SavedNewsInteractor {
        return SavedNewsInteractor(repository)
    }

    @Provides
    fun provideSaveRemoteArticleUseCase(repository: NewsRepositoryImpl) : SaveRemoteArticleUseCase {
        return SaveRemoteArticleUseCase(repository)
    }

    @Provides
    fun provideSearchedNewsUseCase(repository: NewsRepositoryImpl) : SearchedNewsUseCase {
        return SearchedNewsUseCase(repository)
    }
}