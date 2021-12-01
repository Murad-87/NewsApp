package com.example.testapp1.di.app

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationContextModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application
}