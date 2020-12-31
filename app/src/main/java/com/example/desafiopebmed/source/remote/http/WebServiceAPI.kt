package com.example.desafiopebmed.source.remote.http

import com.example.desafiopebmed.source.remote.data.Root
import io.reactivex.Observable
import retrofit2.http.GET

interface WebServiceAPI {
    @GET("contents")
    fun getMedicalList(): Observable<List<Root>>
}