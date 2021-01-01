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
import javax.inject.Inject

class MedicalListViewModel @Inject constructor(
    internal val compositeDisposable: CompositeDisposable,
    private val repository: MedicalListRepository
) : ViewModel(), LifecycleObserver {

    val liveDataMedicalList: MutableSingleLiveData<ViewData<List<RootVO>>> =
        MutableSingleLiveData()

    fun loadMedicalList() =
        compositeDisposable.add(repository
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