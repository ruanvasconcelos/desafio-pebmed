package com.example.desafiopebmed.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.example.desafiopebmed.repository.MedicalListRepository
import com.example.desafiopebmed.repository.vo.RootVO
import com.example.desafiopebmed.viewmodel.utils.MutableSingleLiveData
import com.example.desafiopebmed.viewmodel.utils.ViewData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MedicalListViewModel : ViewModel(), LifecycleObserver {

    val liveDataMedicalList: MutableSingleLiveData<ViewData<List<RootVO>>> =
        MutableSingleLiveData()

    private val compositeDisposable = CompositeDisposable()
    private val medicalListRepository = MedicalListRepository()

    fun loadMedicalList() =
        compositeDisposable.add(medicalListRepository
            .recoverMedicalList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                liveDataMedicalList.postValue(ViewData(ViewData.Status.LOADING))
            }
            .subscribe(
                {
                    liveDataMedicalList.value =
                        ViewData(ViewData.Status.SUCCESS, it)
                },
                {
                    liveDataMedicalList.value =
                        ViewData(ViewData.Status.ERROR, error = it)
                },
                {
                    liveDataMedicalList.value =
                        ViewData(ViewData.Status.COMPLETE, liveDataMedicalList.value?.data)
                })
        )
}