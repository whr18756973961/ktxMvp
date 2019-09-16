package com.whr.baseui.widget

import android.text.TextUtils
import android.view.View
import android.view.ViewStub
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView

import com.whr.baseui.R
import com.whr.baseui.activity.BaseActivity
import com.whr.baseui.fragment.BaseFragment

/**
 * Created by whr on 2017/4/28 0028.
 */

class StatusView {
    /**
     * 是否是fragment的状态
     */
    private var isFragmentStatusView: Boolean = false
    private var mStatusView: View? = null
    private var mViewStubStatusView: ViewStub? = null

    private var mTvStatusMessage: TextView? = null
    private var mIvStatusImage: ImageView? = null
    private var mIvStatusLoading: ImageView? = null
    private var mBtnReplyClick: TextView? = null

    private var mLoadingAnim: Animation? = null
    var mActivity: BaseActivity? = null
    var mFragment: BaseFragment? = null

    constructor(activity: BaseActivity) {
        mActivity = activity
        isFragmentStatusView = false
    }

    constructor(fragment: BaseFragment) {
        mFragment = fragment
        isFragmentStatusView = true
    }

    fun inflate() {
        if (mStatusView != null) return
        if (isFragmentStatusView)
            mViewStubStatusView =
                mFragment!!.mRootView!!.findViewById<View>(R.id.viewstub_status_view) as ViewStub
        else
            mViewStubStatusView =
                mActivity!!.findViewById<View>(R.id.viewstub_status_view) as ViewStub
        mStatusView = mViewStubStatusView!!.inflate()
        mIvStatusImage = mStatusView!!.findViewById(R.id.iv_status_view_image)
        mIvStatusLoading = mStatusView!!.findViewById(R.id.iv_status_view_loading)
        mTvStatusMessage = mStatusView!!.findViewById(R.id.tv_status_view_message)
        mBtnReplyClick = mStatusView!!.findViewById(R.id.btn_status_view_reply)
        mBtnReplyClick!!.setOnClickListener {
            if (isFragmentStatusView)
                mFragment!!.onErrorReplyClick()
            else
                mActivity!!.onErrorReplyClick()
        }
    }

    /**
     * @param message
     */
    fun showEmptyView(message: String) {
        var message = message
        inflate()
        if (TextUtils.isEmpty(message))
            message = "空空如也"
        mTvStatusMessage!!.text = message

        if (mLoadingAnim != null) mLoadingAnim!!.cancel()
        mIvStatusLoading!!.clearAnimation()
        mIvStatusLoading!!.visibility = View.GONE
        mIvStatusImage!!.setImageResource(R.drawable.ic_status_load_empty)
        mStatusView!!.visibility = View.VISIBLE
        mBtnReplyClick!!.visibility = View.GONE
        setContentGone()
    }

    private fun setContentGone() {
        if (isFragmentStatusView)
            mFragment!!.mContentView.visibility = View.GONE
        else
            mActivity!!.mContentView.visibility = View.GONE
    }

    /**
     * @param message
     */
    fun showLoadingView(message: String) {
        var message = message
        inflate()
        if (TextUtils.isEmpty(message))
            message = "Loading"
        mTvStatusMessage!!.text = message

        if (mLoadingAnim == null) {
            mLoadingAnim = AnimationUtils.loadAnimation(mActivity, R.anim.loading_round_rotate)
            mLoadingAnim!!.interpolator = LinearInterpolator()
        }

        mIvStatusLoading!!.visibility = View.VISIBLE
        mIvStatusLoading!!.startAnimation(mLoadingAnim)
        mStatusView!!.visibility = View.VISIBLE
        mIvStatusImage!!.visibility = View.GONE
        mBtnReplyClick!!.visibility = View.GONE
        setContentGone()
    }

    /**
     * 显示错误页面
     *
     * @param message
     */
    fun showErrorView(message: String) {
        var message = message
        inflate()
        if (TextUtils.isEmpty(message))
            message = "加载失败"
        mTvStatusMessage!!.text = message

        if (mLoadingAnim != null) mLoadingAnim!!.cancel()
        mIvStatusLoading!!.clearAnimation()
        mIvStatusLoading!!.visibility = View.GONE
        mIvStatusImage!!.visibility = View.VISIBLE
        mIvStatusImage!!.setImageResource(R.drawable.ic_status_load_error)
        mBtnReplyClick!!.visibility = View.VISIBLE
        mStatusView!!.visibility = View.VISIBLE

        setContentGone()
    }

    /**
     * 隐藏状态页面
     */
    fun hideStatusView() {
        if (mStatusView == null)
            return
        if (mLoadingAnim != null)
            mLoadingAnim!!.cancel()
        mStatusView!!.visibility = View.GONE
        if (isFragmentStatusView)
            mFragment!!.mContentView.visibility = View.VISIBLE
        else
            mActivity!!.mContentView.visibility = View.VISIBLE
    }
}
