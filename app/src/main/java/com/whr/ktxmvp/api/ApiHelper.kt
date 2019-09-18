package com.whr.ktxmvp.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit

/**
 * Created by user on 2019/7/31.
 */

object ApiHelper {
    private var api: ApiServices? = null

    fun api(): ApiServices? {
        if (api == null)
            initApi()
        return api
    }

    /**
     * 初始化api
     */
    fun initApi() {
        // Header
        val headerInter = Interceptor { chain ->
            val builder = chain.request()
                .newBuilder()
            chain.proceed(builder.build())
        }

        val mOkHttpClient = OkHttpClient()
            .newBuilder()
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(headerInter)
            .addInterceptor(LoggingInterceptor())
            .build()
        //网络接口配置
        api = null
        api = Retrofit.Builder()
            .baseUrl("https://www.apiopen.top/")
            .addConverterFactory(ScalarsConverterFactory.create())       //添加字符串的转换器
            .addConverterFactory(GsonConverterFactory.create())          //添加gson的转换器
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())   //添加协程的请求适配器            .client(mOkHttpClient)
            .client(mOkHttpClient)
            .build()
            .create(ApiServices::class.java)
    }


}
