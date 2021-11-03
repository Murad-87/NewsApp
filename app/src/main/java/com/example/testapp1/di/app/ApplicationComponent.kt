package com.example.testapp1.di.app

import android.app.Application
import com.example.testapp1.feature.ui.BreakingNewsFragment
import dagger.BindsInstance
import dagger.Component


@Component(
    modules = [
        ApplicationContextModule::class,
    ]
)
interface ApplicationComponent {
    fun injectBreakingNewsFragment(breakingNewsFragment: BreakingNewsFragment)


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }
}