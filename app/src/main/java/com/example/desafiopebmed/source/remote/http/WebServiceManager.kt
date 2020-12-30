package com.example.desafiopebmed.source.remote.http

class WebServiceManager private constructor() : WebService() {

    companion object {
        val instance  by lazy { WebServiceManager() }
    }

    val webServiceAPI: WebServiceAPI = retrofit.create(WebServiceAPI::class.java)
}