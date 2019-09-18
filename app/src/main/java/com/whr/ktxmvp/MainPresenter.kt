package com.whr.ktxmvp

import com.whr.ktxmvp.api.ApiHelper
import com.whr.ktxmvp.bean.LoginBean
import com.whr.ktxmvp.bean.RegisterBean

class MainPresenter : MainContract.Presenter() {


    override fun requestLogin() {
        view?.showWaitDialog()
        launchRequest(
            {
                ApiHelper.api()?.requestLoginOut()?.await()
            },
            { loginBean: LoginBean? ->
                view?.requestSuccess(loginBean)
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
            { ApiHelper.api()?.requestRegister("123325554444", "123445")?.await() },
            { register: RegisterBean? ->
                view?.requestRegister(register)
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