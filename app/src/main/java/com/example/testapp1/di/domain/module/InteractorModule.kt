package com.example.testapp1.di.domain.module

import com.example.testapp1.business.BreakingNewsInteractor
import com.example.testapp1.business.SaveRemoteArticleUseCase
import com.example.testapp1.business.SavedNewsInteractor
import com.example.testapp1.business.SearchedNewsInteractor
import com.example.testapp1.data.repository.NewsRepository
import dagger.Module
import dagger.Provides

@Module
object InteractorModule {

    @Provides
    @JvmStatic
    fun provideBreakingNewsInteractor(repository: NewsRepository) : BreakingNewsInteractor {
        return BreakingNewsInteractor(repository)
    }

    @Provides
    @JvmStatic
    fun provideSavedNewsInteractor(repository: NewsRepository) : SavedNewsInteractor {
        return SavedNewsInteractor(repository)
    }

    @Provides
    @JvmStatic
    fun provideSaveRemoteArticleUseCase(repository: NewsRepository) : SaveRemoteArticleUseCase {
        return SaveRemoteArticleUseCase(repository)
    }

    @Provides
    @JvmStatic
    fun provideSearchedNewsInteractor(repository: NewsRepository) : SearchedNewsInteractor {
        return SearchedNewsInteractor(repository)
    }
}