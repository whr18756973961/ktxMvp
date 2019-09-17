package com.whr.ktxmvp.api

import android.util.Log
import com.google.gson.Gson

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody

class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        val request = chain.request()

        val t1 = System.nanoTime()//请求发起的时间
//        if (BuildConfig.DEBUG)
//            Log.e("888888888", String.format("发送请求 %s on %s%n%s%n%s",
//                    request.url(), chain.connection(), request.headers(), Gson().toJson(request.body())))

        val response = chain.proceed(request)

        val t2 = System.nanoTime()//收到响应的时间

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        val responseBody = response.peekBody((1024 * 1024).toLong())
        Log.e(
            "888888888", String.format(
                "接收响应: %s %n%s%n%s %n返回json:%s%n%s %n%s",
                response.request().url(),
                response.request().headers(),
                Gson().toJson(response.request().body()),
                responseBody.string(),
                (t2 - t1) / 1e6,
                response.headers()
            )
        )
        return response
    }
}