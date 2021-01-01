package com.example.desafiopebmed.di.module

import android.app.Application
import com.example.desafiopebmed.BuildConfig.DEBUG
import com.example.desafiopebmed.BuildConfig.HOST
import com.example.desafiopebmed.source.remote.http.WebServiceAPI
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetworkModule {
    companion object {
        const val CACHE_HTTP = "http_cache"
        private const val CACHE_SIZE: Long = 50 * 1024 * 1024
        private const val DATE_FORMAT = "dd-MM-yyyy'T'HH:mm:ss"

        private const val NAMED_TIME_OUT = "NAMED_TIME_OUT"

        //Named URL
        const val NAMED_APPLICATION_SERVER_URL = "NAMED_APPLICATION_SERVER_URL"

        //Name Server
        const val NAMED_APPLICATION_SERVER = "NAMED_APPLICATION_SERVER"
    }

    @Provides
    @Singleton
    @Named(NAMED_APPLICATION_SERVER_URL)
    fun provideRestApplicationServerUrl() = HOST

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setDateFormat(DATE_FORMAT)
        .disableHtmlEscaping()
        .create()

    @Provides
    @Singleton
    fun provideGSONConverter(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    @Named(NAMED_TIME_OUT)
    fun provideTimeOut() = 20L

    @Provides
    @Singleton
    fun provideHttpLogging(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
        if (DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    )

    @Provides
    @Singleton
    fun provideCache(fileCache: File) = Cache(fileCache, CACHE_SIZE)

    @Provides
    @Singleton
    fun provideFileCache(application: Application) = File(application.cacheDir, CACHE_HTTP)

    @Provides
    @Singleton
    @Named(NAMED_APPLICATION_SERVER)
    fun provideRetrofitApplicationServer(
        @Named(NAMED_APPLICATION_SERVER_URL) serverUrl: String, cache: Cache,
        @Named(NAMED_TIME_OUT) timeout: Long,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(serverUrl)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(gsonConverterFactory)
        .client(
            OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .build()
        )
        .build()


    @Provides
    @Singleton
    fun provideWebServerApi(@Named(NAMED_APPLICATION_SERVER) retrofit: Retrofit): WebServiceAPI =
        retrofit.create(WebServiceAPI::class.java)

}