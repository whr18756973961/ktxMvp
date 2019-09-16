package com.whr.baseui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.whr.baseui.mvp.BaseMvpPresenter
import com.whr.baseui.mvp.BaseMvpView
import com.whr.baseui.utils.MvpUtils

/**
 * Created by whr on 2018/6/6.
 */

abstract class BaseMvpFragment<V : BaseMvpView, P : BaseMvpPresenter<V>> :
    BaseFragment() {

    var presenter: P? = null
    private fun initMvp() {
        presenter = MvpUtils.getT<P>(this, 1)
        presenter!!.attchView(this as V)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initMvp()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        if (presenter != null) {
            presenter!!.detachView()
            presenter = null
        }
        super.onDestroyView()
    }

}
