package com.whr.baseui.fragment

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import androidx.annotation.LayoutRes

import com.whr.baseui.R
import com.whr.baseui.activity.BaseActivity
import com.whr.baseui.helper.UiCoreHelper
import com.whr.baseui.mvp.BaseMvpView
import com.whr.baseui.swipeback.SwipeBackFragment
import com.whr.baseui.utils.StatusBarUtils
import com.whr.baseui.widget.StatusView

/**
 * Created by whr on 2018/4/16.
 */

abstract class BaseFragment : SwipeBackFragment(), BaseMvpView {

    lateinit var mActivity: BaseActivity
    /**
     * 根布局->fragment_base
     */
    var mRootView: View? = null
    /**
     * 根布局下面的父控件
     */
    lateinit var mRootLayout: LinearLayout

    /**
     * 内容布局->getLayoutId()
     */
    lateinit var mContentView: View

    lateinit var mFakeStatusBar: View

    var mStatusView: StatusView? = null
    /**
     * 頂部導航欄
     */
    lateinit var mHeadView: View
    /**
     * 返回按鈕
     */
    lateinit var mIvBack: ImageView
    /**
     * 標題
     */
    lateinit var mTvTitle: TextView
    /**
     * 右側按鈕
     */
    lateinit var mTvRight: TextView

    lateinit var mIvRight: ImageView

    /**
     * 获取布局文件ID
     */
    @get:LayoutRes
    abstract val layoutId: Int

    val fragment: BaseFragment
        get() = this

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is BaseActivity)
            mActivity = activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            // 初始化基础布局
            mRootView = inflater.inflate(R.layout.fragment_base, container, false)
            mRootLayout = mRootView!!.findViewById<View>(R.id.fragment_base_root) as LinearLayout

            // 虚拟状态栏
            mFakeStatusBar = LayoutInflater.from(context)
                .inflate(R.layout.layout_fake_statusbar, mRootLayout, false)
            mFakeStatusBar.visibility = View.VISIBLE
            val layoutParams = mFakeStatusBar.layoutParams
            layoutParams.height = StatusBarUtils.getStatusBarHeight(activity!!)
            mFakeStatusBar.layoutParams = layoutParams
            mFakeStatusBar.setBackgroundColor(context!!.resources.getColor(UiCoreHelper.getProxyA().colorPrimaryDark()))
            mRootLayout.addView(mFakeStatusBar, 0)
            // 顶部Header
            mHeadView = layoutInflater.inflate(UiCoreHelper.getProxyA().headerIdRes(), null)
            mHeadView.setBackgroundColor(resources.getColor(UiCoreHelper.getProxyA().colorPrimary()))
            initHeadView()
            mRootLayout.addView(mHeadView, 1)
            // 内容布局
            mContentView = LayoutInflater.from(context).inflate(layoutId, mRootLayout, false)
            mRootLayout.addView(mContentView, 2)
        }
        // 缓存的rootView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，
        // 要不然会发生这个rootview已经有parent的错误。
        val parent = mRootView!!.parent as ViewGroup
        parent?.removeView(mRootView)
        rxBusOperate()
        return attachToSwipeBack(mRootView!!)
    }

    fun initHeadView() {
        mIvBack = mHeadView.findViewById(UiCoreHelper.getProxyA().headerBackId())
        mTvTitle = mHeadView.findViewById(UiCoreHelper.getProxyA().headerTitleId())
        mTvRight = mHeadView.findViewById(UiCoreHelper.getProxyA().headerRightId())
        mIvRight = mHeadView.findViewById(UiCoreHelper.getProxyA().headerRightIconId())
        mIvBack.setOnClickListener { mActivity.onActivityFinish() }
    }

    /**
     * 错误界面的点击事件
     */
    fun onErrorReplyClick() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleBundle(arguments)
        initView(view)
    }

    /**
     * 参数校验
     *
     * @param bundle
     */
    fun handleBundle(bundle: Bundle?) {}

    /**
     * rxbus 接口的定义方法
     */
    fun rxBusOperate() {

    }

    /**
     * 初始化控件
     *
     * @param view
     */
    abstract fun initView(view: View)

    /**
     * 设置虚拟状态栏的显隐
     *
     * @param visibility
     */
    fun setFakeStatusBarVis(visibility: Int) {
        mFakeStatusBar.visibility = visibility
    }

    /**
     * header头部的返回键监听时间
     */
    fun onHeaderBackPressed() {
        mActivity.onActivityBackPressed()
    }


    override fun setSwipeBackEnable(enable: Boolean) {
        var enable = enable
        val count = mActivity.supportFragmentManager.backStackEntryCount
        if (mActivity.layoutId == 0) {
            enable = count > 1
        }
        super.setSwipeBackEnable(enable)
    }

    /**
     * 显示对话框
     */
    override fun showWaitDialog() {
        mActivity.showWaitDialog()
    }

    override fun showWaitDialog(message: String) {
        mActivity.showWaitDialog(message)
    }

    override fun showWaitDialog(message: String, cancelable: Boolean) {
        mActivity.showWaitDialog(message, cancelable)
    }

    override fun isWaitDialogShow(): Boolean {
        return mActivity.isWaitDialogShow()
    }

    override fun getWaitDialog(): Dialog {
        return mActivity.getWaitDialog()
    }

    override fun getmActivity(): BaseActivity {
        return mActivity
    }

    override fun getmFragment(): BaseFragment ?{
        return null
    }

    override fun hideWaitDialog() {
        mActivity.hideWaitDialog()
    }

    override fun showToast(msg: String?) {
        mActivity.showToast(msg)
    }

    override fun showToast(strId: Int) {
        mActivity.showToast(strId)
    }

    /**
     * 此方法会在fragment ，显示是调用，相当于fragment的onresume方法，可以做返回刷新UI等操作
     */
    fun onFragmentVisiable() {

    }

    private fun initStatusView() {
        if (mStatusView == null)
            mStatusView = StatusView(this)
    }

    override fun showStatusEmptyView(emptyMessage: String) {
        initStatusView()
        mStatusView!!.showEmptyView(emptyMessage)
    }

    override fun showStatusErrorView(emptyMessage: String) {
        initStatusView()
        mStatusView!!.showErrorView(emptyMessage)
    }

    override fun showStatusLoadingView(loadingMessage: String) {
        initStatusView()
        mStatusView!!.showLoadingView(loadingMessage)
    }

    override fun showStatusLoadingView(loadingMessage: String, isHasMinTime: Boolean) {

    }

    override fun hideStatusView() {
        if (mStatusView != null)
            mStatusView!!.hideStatusView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        UiCoreHelper.getProxyA().onFragmentActivityCreated(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UiCoreHelper.getProxyA().onFragmentCreate(this)
    }

    override fun onStart() {
        super.onStart()
        UiCoreHelper.getProxyA().onFragmentStart(this)
    }

    override fun onStop() {
        super.onStop()
        UiCoreHelper.getProxyA().onFragmentStop(this)
    }

    override fun onPause() {
        super.onPause()
        UiCoreHelper.getProxyA().onFragmentPause(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        UiCoreHelper.getProxyA().onFragmentDestroyView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        UiCoreHelper.getProxyA().onFragmentDestroy(this)
    }

    override fun onDetach() {
        super.onDetach()
        UiCoreHelper.getProxyA().onFragmentDetach(this)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            onFragmentVisiable()
        }
    }

    override fun onResume() {
        super.onResume()
        UiCoreHelper.getProxyA().onFragmentResume(this)
        if (!isHidden) {
            onFragmentVisiable()
        }
    }

    companion object {
        val FFCID = R.id.fragment_base_root
    }
}
