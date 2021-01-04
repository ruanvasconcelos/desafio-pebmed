package com.example.desafiopebmed.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desafiopebmed.di.ViewModelFactory
import com.example.desafiopebmed.di.ViewModelKey
import com.example.desafiopebmed.viewmodel.DetailsViewModel
import com.example.desafiopebmed.viewmodel.MedicalListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MedicalListViewModel::class)
    internal abstract fun medicalListViewModel(medicalListViewModel: MedicalListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    internal abstract fun detailsViewModel(detailsViewModel: DetailsViewModel): ViewModel

}
