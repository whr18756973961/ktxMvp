package com.whr.ktxmvp.api

import com.whr.baseui.bean.Result
import com.whr.ktxmvp.bean.LoginBean
import com.whr.ktxmvp.bean.RegisterBean
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    /**
     * 用户登录
     */
    @GET("login?key=00d91e8e0cca2b76f515926a36db68f5")
    fun requestLoginOut( @Query("phone") phone: String,
                         @Query("passwd") passwd: String): Deferred<Result<LoginBean>>

    @GET("createUser?key=00d91e8e0cca2b76f515926a36db68f5")
    fun requestRegister(
        @Query("phone") phone: String,
        @Query("passwd") passwd: String
    ): Deferred<Result<RegisterBean>>

}
