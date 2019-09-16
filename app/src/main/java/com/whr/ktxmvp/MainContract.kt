package com.whr.ktxmvp

import com.whr.baseui.mvp.BaseMvpView
import com.xiaozhoudao.loanassistant.api.KotlinPresenter

interface MainContract {
    interface View : BaseMvpView {
        fun requestSuccess(str: String)

        fun requestError(str: String?)
    }

    abstract class Presenter : KotlinPresenter<View>() {
        internal abstract fun request()
    }
}