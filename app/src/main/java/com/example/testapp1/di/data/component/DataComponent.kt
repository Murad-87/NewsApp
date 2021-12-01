package com.example.testapp1.di.data.component

import com.example.testapp1.data.repository.NewsRepository
import com.example.testapp1.di.data.DataScope
import com.example.testapp1.di.app.ApplicationComponent
import com.example.testapp1.di.data.module.LocaleModule
import com.example.testapp1.di.data.module.RemoteModule
import com.example.testapp1.di.data.module.RepositoryModule
import dagger.Component

@Component(
    dependencies = [ApplicationComponent::class],
    modules = [
        LocaleModule::class,
        RemoteModule::class,
        RepositoryModule::class
    ]
)
@DataScope
interface DataComponent {
    fun provideRepository(): NewsRepository
}