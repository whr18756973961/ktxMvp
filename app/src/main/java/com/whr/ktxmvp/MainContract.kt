package com.whr.ktxmvp

import com.whr.baseui.mvp.BaseMvpView
import com.whr.baseui.mvp.KotlinPresenter
import com.whr.ktxmvp.api.LoginBean

interface MainContract {
    interface View : BaseMvpView {
        fun requestSuccess(loginBean: LoginBean?)

        fun requestError(str: String?)
    }

    abstract class Presenter : KotlinPresenter<View>() {
        internal abstract fun request()
    }
}