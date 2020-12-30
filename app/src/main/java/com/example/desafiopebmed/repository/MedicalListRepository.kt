package com.example.desafiopebmed.repository

import com.example.desafiopebmed.source.remote.http.WebServiceManager


class MedicalListRepository {
    private val webServiceManager = WebServiceManager.instance

    fun recoverMedicalList() = webServiceManager.webServiceAPI.getMedicalList()

}