package com.whr.ktxmvp

import com.whr.ktxmvp.api.ApiHelper

class MainPresenter : MainContract.Presenter() {
    override fun request() {
        view?.showWaitDialog()
        launchWithTryCatch(
            {
                var response = ApiHelper.api()?.requestLoginOut()?.await()
                callResponse(
                    response,
                    {
                        view?.requestSuccess(response?.getData())
                    },
                    {
                        view?.requestError(response?.getMessage())
                    }
                )
            },
            { e ->
                view?.requestError(e)
            },
            {
                view?.hideWaitDialog()
            })

    }
}