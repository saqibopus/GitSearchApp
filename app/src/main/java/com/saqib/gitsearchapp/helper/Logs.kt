package com.saqib.gitsearchapp.helper

import com.saqib.gitsearchapp.BuildConfig


object Logs {
    const val TAG = "----**"
    fun p(string: String) {
        if (BuildConfig.DEBUG) {
            println("$TAG $string")
        }
    }
}