package com.example.testapp1

import android.app.Application
import com.example.testapp1.di.app.ApplicationComponent



class NewsApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
    }

}