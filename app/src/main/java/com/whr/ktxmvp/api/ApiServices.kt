package com.whr.ktxmvp.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiServices {

    @GET("journalismApi")
    fun requestLoginOut(): Deferred<Result<String>>
}
