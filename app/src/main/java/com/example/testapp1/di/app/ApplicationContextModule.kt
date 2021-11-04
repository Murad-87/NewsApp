package com.example.testapp1.di.app

import android.content.Context
import com.example.testapp1.NewsApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApplicationContextModule(private var application: NewsApplication) {

    @Provides
    @Singleton
    fun getContext(): Context = application
}