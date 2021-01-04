package com.example.desafiopebmed.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.example.desafiopebmed.repository.DetailsRepository
import com.example.desafiopebmed.repository.vo.ContentVO
import com.example.desafiopebmed.viewmodel.utils.MutableSingleLiveData
import com.example.desafiopebmed.viewmodel.utils.ViewData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    internal val compositeDisposable: CompositeDisposable,
    private val repository: DetailsRepository
) : ViewModel(), LifecycleObserver {

    val liveDataContentDetails: MutableSingleLiveData<ViewData<ContentVO>> =
        MutableSingleLiveData()

    fun loadContentDetails(id :Int?) =
        compositeDisposable.add(repository
            .loadContent(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                liveDataContentDetails.postValue(ViewData(ViewData.Status.LOADING))
            }
            .subscribe(
                {
                    liveDataContentDetails.value =
                        ViewData(ViewData.Status.SUCCESS, it)
                },
                {
                    liveDataContentDetails.value =
                        ViewData(ViewData.Status.ERROR, error = it)
                },
                {
                    liveDataContentDetails.value =
                        ViewData(ViewData.Status.COMPLETE, liveDataContentDetails.value?.data)
                })
        )
}