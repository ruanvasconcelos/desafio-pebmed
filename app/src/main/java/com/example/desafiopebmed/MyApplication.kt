package com.example.desafiopebmed

import com.example.desafiopebmed.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class MyApplication : DaggerApplication() {
    companion object {
        lateinit var instance: MyApplication
            private set
    }

    private var baseURL = BuildConfig.HOST

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .apply {
                inject(this@MyApplication)
            }
    
}