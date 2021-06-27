package com.saqib.gitsearchapp.helper

object Constants {
    object ENDPOINTS {
        const val BASE_URL = "https://api.github.com"
        const val GIT_SEARCH = "search/repositories"
    }
    object CACHE {
        const val CACHE_FILE_NAME   = "github-search-cache"
        const val CACHE_SIZE : Long = 10 * 1024 * 1024 // 10 MB
    }
    object ErrorCodes{
        const val OK200 = 200
        const val ERROR401 = 401
    }
    object ErrorMessage{
        const val COMMON_UN_IDENTIFIED = "Oops..Something went wrong"
        const val NO_DESC_AVAILABLE = "No description available"
        const val NO_TITLE_AVAILABLE = "No title available"
    }
}