package com.whr.baseui.mvp

/**
 * Created by whr on 2018/6/6.
 */

abstract class BaseMvpPresenter<V : BaseMvpView> {
    var view: V? = null

    fun attchView(v: V) {
        view = v
    }

    open fun detachView() {
        this.view = null
    }
}
