package com.example.desafiopebmed.di

import android.content.Context
import dagger.android.HasAndroidInjector

object WorkerInjector {
    @JvmStatic
    fun inject(to: Any, with: Context) {
        (with.applicationContext as? HasAndroidInjector)?.androidInjector()?.inject(to)
    }
}