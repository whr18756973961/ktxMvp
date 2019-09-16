package com.whr.ktxmvp.api

import android.app.Application
import android.content.Context
import com.whr.baseui.helper.UiCoreHelper
import com.whr.ktxmvp.impl.UiCoreProxyImpl

/**
 * Created by user on 2019/7/29.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
        initUiCoreProxy()
    }

    companion object {
        lateinit var appContext: Context
    }

    fun initUiCoreProxy() {
        UiCoreHelper.setProxyA(UiCoreProxyImpl())
    }

}

