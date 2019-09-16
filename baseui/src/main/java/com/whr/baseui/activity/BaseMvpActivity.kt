package com.whr.baseui.activity

import android.os.Bundle

import com.whr.baseui.mvp.BaseMvpPresenter
import com.whr.baseui.mvp.BaseMvpView
import com.whr.baseui.utils.MvpUtils


/**
 * Created by whr on 2018/6/6.
 */

abstract class BaseMvpActivity<V : BaseMvpView, P : BaseMvpPresenter<V>> : BaseActivity() {
    var presenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        initMvp()
        super.onCreate(savedInstanceState)
    }

    private fun initMvp() {
        presenter = MvpUtils.getT<P>(this, 1)
        presenter!!.attchView(this as V)
    }

    override fun onDestroy() {
        if (presenter != null) {
            presenter!!.detachView()
            presenter = null
        }
        super.onDestroy()
    }


}
