package com.saqib.gitsearchapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseVM : ViewModel() {

    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val hasError: MutableLiveData<String> = MutableLiveData()
    private val disposable: CompositeDisposable = CompositeDisposable()


    fun showLoading() {
        isLoading.postValue(true)
    }

    fun hideLoading() {
        isLoading.postValue(false)
    }

    fun hasError(error: String?) {
        hasError.postValue(error)
    }

    fun getDisposable(): CompositeDisposable {
        return disposable
    }

    fun getLoading(): MutableLiveData<Boolean> {
        return isLoading
    }

    fun getError(): MutableLiveData<String> {
        return hasError
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}