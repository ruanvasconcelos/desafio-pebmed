package com.example.desafiopebmed.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.example.desafiopebmed.source.remote.data.Root
import com.example.desafiopebmed.viewmodel.utils.MutableSingleLiveData
import com.example.desafiopebmed.viewmodel.utils.ViewData

class MedicalListViewModel : ViewModel(), LifecycleObserver{

    val liveDataMedicalList: MutableSingleLiveData<ViewData<List<Root>>> =
        MutableSingleLiveData()
}