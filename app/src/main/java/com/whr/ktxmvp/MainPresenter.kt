package com.whr.ktxmvp

import com.whr.ktxmvp.api.ApiHelper
import com.whr.ktxmvp.bean.LoginBean
import com.whr.ktxmvp.bean.RegisterBean

class MainPresenter : MainContract.Presenter() {
    val phone = "135545842326"
    val password = "123456"

    override fun requestLogin() {
        view?.showWaitDialog()
        launchRequest(
            {
                ApiHelper.api()?.requestLoginOut(phone,password)?.await()
            },
            { loginBean: LoginBean? ->
                view?.requestLoginSuccess(loginBean)
            },
            { errMsg: String? ->
                view?.requestError(errMsg)
            },
            {
                view?.hideWaitDialog()
            })

    }

    override fun requestRegister() {
        view?.showWaitDialog()
        launchRequest(
            { ApiHelper.api()?.requestRegister(phone,password)?.await() },
            { register: RegisterBean? ->
                view?.requestRegisterSuccess(register)
            },
            { errMsg: String? ->
                view?.requestError(errMsg)
            },
            {
                view?.hideWaitDialog()
            }
        )
    }
}