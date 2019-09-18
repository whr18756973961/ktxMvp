package com.whr.ktxmvp

import com.whr.baseui.mvp.BaseMvpView
import com.whr.baseui.mvp.KotlinPresenter
import com.whr.ktxmvp.bean.LoginBean
import com.whr.ktxmvp.bean.RegisterBean

interface MainContract {
    interface View : BaseMvpView {
        fun requestSuccess(loginBean: LoginBean?)

        fun requestError(str: String?)

        fun requestRegister(registerBean: RegisterBean?)
    }

    abstract class Presenter : KotlinPresenter<View>() {
        internal abstract fun requestLogin()

        internal abstract fun requestRegister()
    }
}