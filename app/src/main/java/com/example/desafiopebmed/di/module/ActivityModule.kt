package com.example.desafiopebmed.di.module


import com.example.desafiopebmed.ui.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): HomeActivity
}