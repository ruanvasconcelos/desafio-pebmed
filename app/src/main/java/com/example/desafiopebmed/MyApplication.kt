package com.example.desafiopebmed

import android.app.Application
import androidx.annotation.VisibleForTesting

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
            private set
    }

    private var baseURL = BuildConfig.HOST

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getBaseURL() = this.baseURL

    @VisibleForTesting
    fun setBaseURL(baseURL: String) {
        this.baseURL = baseURL
    }
}