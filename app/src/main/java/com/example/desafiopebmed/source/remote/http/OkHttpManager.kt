package com.example.desafiopebmed.source.remote.http

import com.example.desafiopebmed.BuildConfig.DEBUG
import com.example.desafiopebmed.MyApplication
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import java.io.File
import java.util.concurrent.TimeUnit

class OkHttpManager {

    companion object {
        private const val CACHE_NAME = "NOTICIA"
        private const val DISK_CACHE_SIZE = 50 * 1024 * 1024L
        private const val TIME_OUT = 20L
    }

    val okHttpClient: OkHttpClient

    init {
        val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(if (DEBUG) Level.BODY else Level.NONE)

        okHttpClient = OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .cache(Cache(File(MyApplication.instance.cacheDir, CACHE_NAME), DISK_CACHE_SIZE))
            .followRedirects(false)
            .addInterceptor(loggingInterceptor)
            .build()
    }
}