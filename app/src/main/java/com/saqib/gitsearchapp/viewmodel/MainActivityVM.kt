package com.saqib.gitsearchapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saqib.gitsearchapp.base.BaseVM
import com.saqib.gitsearchapp.helper.Constants
import com.saqib.gitsearchapp.model.GitSearchListModel
import com.saqib.gitsearchapp.model.SearchParams
import com.saqib.gitsearchapp.networking.ApiClient
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MainActivityVM() : BaseVM() {
    fun getRepoLists(searchParams: SearchParams): LiveData<GitSearchListModel> {
        val model: MutableLiveData<GitSearchListModel> = MutableLiveData()
        showLoading()
        ApiClient
            .getClient()
            .searchGithub(
                searchParams.searchParam,
                searchParams.page,
                searchParams.sort,
                searchParams.order,
                searchParams.perPage
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response<GitSearchListModel>> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                    getDisposable().add(d)
                }

                override fun onNext(t: Response<GitSearchListModel>) {
                    hideLoading()
                    when {
                        t.code() == Constants.ErrorCodes.OK200 -> {
                            model.postValue(t.body())
                        }
                        t.code() == Constants.ErrorCodes.ERROR401 -> {
                            hasError(Constants.ErrorMessage.COMMON_UN_IDENTIFIED)
                        }
                        else -> {
                            hasError(Constants.ErrorMessage.COMMON_UN_IDENTIFIED)
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    hideLoading()
                    hasError("${e.cause}")
                }
            })
        return model
    }

}