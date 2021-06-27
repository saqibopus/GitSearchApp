package com.saqib.gitsearchapp.model


data class SearchParams(
    val searchParam : String,
    val page : Int,
    val sort : String = "",
    val order : String = "desc",
    val perPage : Int = 10
)