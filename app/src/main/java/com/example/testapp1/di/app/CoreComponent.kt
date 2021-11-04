package com.example.testapp1.di.app

import android.content.Context
import com.example.testapp1.feature.NewsActivity
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [CoreModule::class]
)
@Singleton
interface CoreComponent {
    fun context(): Context
    fun inject(activity: NewsActivity)
}