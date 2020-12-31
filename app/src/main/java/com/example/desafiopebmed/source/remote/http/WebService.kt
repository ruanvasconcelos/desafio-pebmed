package com.example.desafiopebmed.source.remote.http

import com.example.desafiopebmed.MyApplication
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

open class WebService {

    private val okHttpManager: OkHttpManager = OkHttpManager()
    protected val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(MyApplication.instance.getBaseURL())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpManager.okHttpClient)
            .build()

}