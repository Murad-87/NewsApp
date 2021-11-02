package com.example.testapp1.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides


@Module
object ApplicationContextModule {

    @Provides
    @JvmStatic
    fun provideContext(application: Application): Context =application.applicationContext
}