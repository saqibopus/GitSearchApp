package com.saqib.gitsearchapp.networking

import com.saqib.gitsearchapp.helper.Constants
import com.saqib.gitsearchapp.model.GitSearchListModel
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestInterface {

    @GET(Constants.ENDPOINTS.GIT_SEARCH)
    fun searchGithub(
        @Query("q")         searchParam : String,
        @Query("page")      page : Int,
        @Query("sort")      sort : String,
        @Query("order")     order : String,
        @Query("per_page")  perPage : Int ) : Observable<Response<GitSearchListModel>>

}