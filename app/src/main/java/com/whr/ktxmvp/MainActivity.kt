package com.whr.ktxmvp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.whr.baseui.activity.BaseMvpActivity
import com.whr.baseui.fragment.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpActivity<MainContract.View, MainPresenter>(), MainContract.View {
    override fun requestSuccess(str: String) {
    }

    override fun requestError(str: String?) {
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initView(rootView: View) {
        mHeadView.visibility = View.GONE

        btn_start.setOnClickListener { presenter?.request() }
    }
}
