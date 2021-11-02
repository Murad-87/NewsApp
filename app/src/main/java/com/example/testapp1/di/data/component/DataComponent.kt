package com.example.testapp1.di.data.component

import com.example.testapp1.data.local.dao.ArticleDao
import com.example.testapp1.data.remote.api.NewsAPI
import com.example.testapp1.di.data.module.LocaleModule
import com.example.testapp1.di.data.module.RemoteModule
import com.example.testapp1.di.module.ApplicationContextModule
import dagger.Component

@Component(modules = [
    ApplicationContextModule::class,
    LocaleModule::class,
    RemoteModule::class
])
interface DataComponent {

    fun provideDao() : ArticleDao
    fun provideApi() : NewsAPI
}