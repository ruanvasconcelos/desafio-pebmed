package com.example.desafiopebmed.di.module

import com.example.desafiopebmed.repository.DetailsRepository
import com.example.desafiopebmed.repository.MedicalListRepository
import com.example.desafiopebmed.source.local.Database
import com.example.desafiopebmed.source.remote.http.WebServiceAPI
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesMedicalListRepository(
        webServiceAPI: WebServiceAPI,
        dataBase: Database
    ) = MedicalListRepository(webServiceAPI, dataBase)

    @Provides
    fun providesDetailsRepository(
        dataBase: Database
    ) = DetailsRepository(dataBase)
}