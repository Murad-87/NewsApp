package com.example.testapp1.di.app

import android.content.Context
import dagger.Component

@Component(
    modules = [
        ApplicationContextModule::class,
    ]
)
interface ApplicationComponent {
    fun appContext() : Context
}