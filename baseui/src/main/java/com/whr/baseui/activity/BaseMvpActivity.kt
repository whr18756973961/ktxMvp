package com.whr.baseui.activity

import android.os.Bundle

import com.whr.baseui.mvp.BaseMvpPresenter
import com.whr.baseui.mvp.BaseMvpView
import com.whr.baseui.utils.MvpUtils


/**
 * Created by whr on 2018/6/6.
 */

abstract class BaseMvpActivity<P : BaseMvpPresenter<BaseMvpView>> : BaseActivity() {
    var presenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        initMvp()
        super.onCreate(savedInstanceState)
    }

    private fun initMvp() {
        presenter = MvpUtils.getT<P>(this, 0)
        presenter!!.attchView(this)
    }

    override fun onDestroy() {
        if (presenter != null) {
            presenter!!.detachView()
            presenter = null
        }
        super.onDestroy()
    }


}
