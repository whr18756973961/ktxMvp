package com.whr.baseui.mvp

import android.app.Dialog

import com.whr.baseui.activity.BaseActivity
import com.whr.baseui.fragment.BaseFragment

/**
 * Created by whr on 2018/6/6.
 */

interface BaseMvpView {

    fun isWaitDialogShow(): Boolean

    fun getWaitDialog(): Dialog

    fun getmFragment(): BaseFragment?

    fun showWaitDialog()

    fun showWaitDialog(message: String)

    fun showWaitDialog(message: String, cancelable: Boolean)

    fun getmActivity(): BaseActivity

    fun hideWaitDialog()

    fun showToast(msg: String?)

    fun showToast(strId: Int)

    fun showStatusEmptyView(emptyMessage: String)

    fun showStatusErrorView(emptyMessage: String)

    fun showStatusLoadingView(loadingMessage: String)

    fun showStatusLoadingView(loadingMessage: String, isHasMinTime: Boolean)

    fun hideStatusView()

}
