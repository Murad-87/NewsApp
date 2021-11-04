package com.example.testapp1.di.data.component

import com.example.testapp1.data.repository.NewsRepository
import com.example.testapp1.di.app.ApplicationContextModule
import com.example.testapp1.di.data.module.LocaleModule
import com.example.testapp1.di.data.module.RemoteModule
import com.example.testapp1.di.data.module.RepositoryModule
import dagger.Component

@Component(
    modules = [
        ApplicationContextModule::class,
        LocaleModule::class,
        RemoteModule::class,
        RepositoryModule::class
    ]
)
interface DataComponent {
    fun provideRepository(): NewsRepository
}