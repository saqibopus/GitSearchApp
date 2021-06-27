package com.saqib.gitsearchapp.networking

import com.saqib.gitsearchapp.BuildConfig
import com.saqib.gitsearchapp.helper.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val NETWORK_CALL_TIMEOUT = 60.toLong()

    private fun interceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    private fun httpClientClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor())
            .build()
    }

    fun getClient(): RequestInterface {
        return Retrofit.Builder()
            .baseUrl(Constants.ENDPOINTS.BASE_URL)
            .client(httpClientClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RequestInterface::class.java)
    }

}