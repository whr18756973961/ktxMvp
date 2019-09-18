package com.whr.ktxmvp

import android.view.View
import com.google.gson.Gson
import com.whr.baseui.activity.BaseMvpActivity
import com.whr.ktxmvp.bean.LoginBean
import com.whr.ktxmvp.bean.RegisterBean
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpActivity<MainContract.View, MainPresenter>(), MainContract.View {
    override fun requestRegisterSuccess(registerBean: RegisterBean?) {
        tv_txt.setText(Gson().toJson(registerBean))
    }

    override fun requestLoginSuccess(loginBean: LoginBean?) {
        tv_txt.setText(Gson().toJson(loginBean))
    }

    override fun requestError(str: String?) {
        showToast(str)
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initView(rootView: View) {
        mHeadView.visibility = View.GONE
        btn_start.setOnClickListener {
            tv_txt.setText("")
            presenter?.requestLogin()
        }
        btn_register.setOnClickListener {
            tv_txt.setText("")
            presenter?.requestRegister()
        }
    }
}
