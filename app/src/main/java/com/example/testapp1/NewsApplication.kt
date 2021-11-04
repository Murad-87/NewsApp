package com.example.testapp1

import android.app.Application
import com.example.testapp1.di.app.AppComponentProvider
import com.example.testapp1.di.app.ApplicationComponent
import com.example.testapp1.di.app.ApplicationContextModule
import com.example.testapp1.di.app.DaggerApplicationComponent

class NewsApplication : Application(), AppComponentProvider {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initComponent()
    }

    private fun initComponent() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationContextModule(ApplicationContextModule(this))
            .build()
    }

    override fun provideCoreComponent(): ApplicationComponent {
        if (this::applicationComponent.isInitialized.not()) {
            initComponent()
        }
        return applicationComponent
    }
}