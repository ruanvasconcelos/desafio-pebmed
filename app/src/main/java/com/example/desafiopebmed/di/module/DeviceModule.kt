package com.example.desafiopebmed.di.module


import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.disposables.CompositeDisposable

@Module
class DeviceModule {
    @Provides
    fun compositeDisposable() = CompositeDisposable()
    
}