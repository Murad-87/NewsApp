package com.example.testapp1

import android.app.Application
import com.example.testapp1.di.DaggerApplicationComponent

class NewsApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}