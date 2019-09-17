package com.whr.ktxmvp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.whr.baseui.activity.BaseMvpActivity
import com.whr.baseui.fragment.BaseFragment
import com.whr.ktxmvp.api.LoginBean
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpActivity<MainContract.View, MainPresenter>(), MainContract.View {
    override fun requestSuccess(loginBean: LoginBean?) {
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
            presenter?.request()
        }
    }
}
