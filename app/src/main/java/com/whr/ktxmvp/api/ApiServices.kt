package com.whr.ktxmvp.api

import com.whr.baseui.bean.Result
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiServices {

    @GET("login?key=00d91e8e0cca2b76f515926a36db68f5&phone=13594347817&passwd=123456")
    fun requestLoginOut(): Deferred<Result<LoginBean>>
}
