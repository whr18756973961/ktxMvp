package com.whr.baseui.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.text.TextUtils
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDelegate

import com.whr.baseui.R
import com.whr.baseui.fragment.BaseFragment
import com.whr.baseui.helper.UiCoreHelper
import com.whr.baseui.mvp.BaseMvpView
import com.whr.baseui.swipeback.SwipeBackActivity
import com.whr.baseui.utils.AppManager
import com.whr.baseui.utils.FragmentUtils
import com.whr.baseui.utils.StatusBarDarkUtil
import com.whr.baseui.utils.StatusBarUtils
import com.whr.baseui.widget.StatusView
import com.whr.baseui.widget.WaitProgressDialog


/**
 * Created by whr on 2018/4/16.
 */

abstract class BaseActivity : SwipeBackActivity(), BaseMvpView, View.OnClickListener {

    lateinit var mActivity: BaseActivity
    /**
     * 父布局
     */
    /**
     * return R.layout.activity_base
     *
     * @return
     */
    lateinit var rootView: FrameLayout

    lateinit var mContentView: View

    /**
     * 控件声明的集合
     */
    private var mViews: SparseArray<View>? = null

    /**
     * 对话框
     */
    private var mWaitDialog: WaitProgressDialog? = null
    /**
     * 是否支持双击，默认为不支持
     */
    private val mDoubleClickEnable = false
    /**
     * 上一次点击的时间戳
     */
    private var mLastClickTime: Long = 0
    /**
     * 被判断为重复点击的时间间隔
     */
    private val MIN_CLICK_DELAY_TIME: Long = 300

    var mStatusView: StatusView? = null

    lateinit var mFakeStatusBar: View

    lateinit var mRootView: LinearLayout
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
     * 右側文字按鈕
     */
    lateinit var mTvRight: TextView
    /**
     * 右侧图片按钮
     */
    lateinit var mIvRight: ImageView
    /**
     * 底部按钮
     */
    lateinit var mBtmLine: View
    /**
     * 计算起始时间
     */
    var onStartTimeMills: Long? = 0L

    /**
     * 设置应用布局时是否考虑系统窗口布局<br></br>
     * 默认是true
     *
     * @return
     */
    val isFitsSystemWindows: Boolean
        get() = true

    /**
     * 返回页面布局
     *
     * @return
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * 检测双击
     */
    val isDoubleClick: Boolean
        get() {
            if (mDoubleClickEnable) return false
            val time = System.currentTimeMillis()
            if (time - mLastClickTime > MIN_CLICK_DELAY_TIME) {
                mLastClickTime = time
                return false
            } else {
                return true
            }
        }


    val pageTime: Long?
        get() = System.currentTimeMillis() - onStartTimeMills!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        AppManager.getAppManager().addActivity(this)
        UiCoreHelper.getProxyA().onActivityCreate(this)
        onStartTimeMills = System.currentTimeMillis()
        mActivity = this
        setStatusBar()
        mRootView = layoutInflater.inflate(R.layout.activity_base, null) as LinearLayout
        // 虚拟導航状态栏
        mFakeStatusBar =
            LayoutInflater.from(this).inflate(R.layout.layout_fake_statusbar, mRootView, false)
        mFakeStatusBar.visibility = View.VISIBLE
        val layoutParams = mFakeStatusBar.layoutParams
        layoutParams.height = StatusBarUtils.getStatusBarHeight(this)
        mFakeStatusBar.layoutParams = layoutParams
        mFakeStatusBar.setBackgroundColor(resources.getColor(UiCoreHelper.getProxyA().colorPrimaryDark()))
        mRootView.addView(mFakeStatusBar, 0)
        mHeadView = layoutInflater.inflate(UiCoreHelper.getProxyA().headerIdRes(), null)
        initHeadView()
        rootView = mRootView.findViewById(R.id.fl_container)
        mRootView.addView(mHeadView, 1)
        if (layoutId != 0) {
            mContentView = layoutInflater.inflate(layoutId, null)
            rootView.addView(mContentView, -1, -1)
        }
        setContentView(mRootView)
        if (null != intent) handleIntent(intent)
        initView(mRootView)
        rxBusOperate()
        if (Build.VERSION.SDK_INT == 26) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    fun initHeadView() {
        mIvBack = mHeadView.findViewById(UiCoreHelper.getProxyA().headerBackId())
        mTvTitle = mHeadView.findViewById(UiCoreHelper.getProxyA().headerTitleId())
        mTvRight = mHeadView.findViewById(UiCoreHelper.getProxyA().headerRightId())
        mIvRight = mHeadView.findViewById(UiCoreHelper.getProxyA().headerRightIconId())
        mIvBack.setOnClickListener { onActivityFinish() }
        mBtmLine = mHeadView.findViewById(UiCoreHelper.getProxyA().headerBtmLineId())
    }

    fun <V : View> findView(@IdRes viewId: Int, click: Boolean): V {
        if (mViews == null) mViews = SparseArray()
        var view: V? = mViews!!.get(viewId) as V
        if (view != null) return view
        view = findViewById<View>(viewId) as V
        mViews!!.put(viewId, view)
        if (click) view.setOnClickListener(this)
        return view
    }

    fun <V : View> findView(@IdRes viewId: Int): V {
        return findView(viewId, false)
    }

    /**
     * @param view
     * @param <E>
    </E> */
    fun <E : View> setOnClick(view: E?) {
        if (view == null) return
        view.setOnClickListener(this)
    }

    override fun onBackPressed() {
        onActivityFinish()
    }

    /**
     * 设置状态栏颜色
     */
    fun setStatusBar() {
        StatusBarUtils.setTranslucent(this, 0)
        setStatusBarStyle(true)
    }

    /**
     * 默认黑色状态栏主题，就是状态栏字体黑色
     */
    fun setStatusBarStyle(isDark: Boolean) {
        StatusBarDarkUtil.setStatusBarMode(this, isDark)
    }

    /**
     * 是否需要透明状态栏的键盘适配
     *
     * @return
     */
    fun fitKeyboard(): Boolean {
        return true
    }

    /**
     * 是否是全屏页面
     *
     * @return
     */
    fun fullScreen(): Boolean {
        return true
    }

    /**
     * 获取Intent
     *
     * @param intent
     */
    fun handleIntent(intent: Intent) {}

    /**
     * 初始化控件
     *
     * @param rootView
     */
    protected abstract fun initView(rootView: View)

    /**
     * 处理返回操作
     *
     * @return
     */
    fun onActivityBackPressed(): Boolean {
        // 如果getLayoutId()==0说明没有加载Activity自己的布局，只是单独加载了Fragment
        val count = supportFragmentManager.backStackEntryCount

        if (layoutId == 0 && count == 1) {
            onActivityFinish()
            return true
        }

        if (layoutId != 0 && count == 0) {
            onActivityFinish()
            return true
        }
        FragmentUtils.popFragment(supportFragmentManager)
        return false
    }

    /**
     * 当activity结束时候调用
     */
    fun onActivityFinish() {
        finish()
    }

    override fun swipeBackPriority(): Boolean {
        return if (layoutId == 0) {
            super.swipeBackPriority()
        } else {
            supportFragmentManager.backStackEntryCount <= 0
        }
    }

    override fun showWaitDialog() {
        showWaitDialog("Loading")
    }

    override fun showWaitDialog(message: String) {
        if (TextUtils.isEmpty(message))
            showWaitDialog()
        else
            showWaitDialog(message, true)
    }

    override fun showWaitDialog(message: String, cancelable: Boolean) {
        if (mWaitDialog == null) {
            mWaitDialog = WaitProgressDialog(this)
        }
        mWaitDialog!!.show(message, cancelable)
    }

    override fun getmActivity(): BaseActivity {
        return mActivity
    }

    override fun getmFragment(): BaseFragment? {
        return null
    }

    override fun isWaitDialogShow(): Boolean {
        return mActivity.isWaitDialogShow()
    }

    override fun getWaitDialog(): Dialog {
        return mActivity.getWaitDialog()
    }

    override fun hideWaitDialog() {
        if (mWaitDialog != null && mWaitDialog!!.isShowing) {
            mWaitDialog!!.dismiss()
        }
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    override fun showToast(strId: Int) {
        Toast.makeText(this, getString(strId), Toast.LENGTH_SHORT).show()
    }

    override fun showToast(strId1: Int, str: Int) {
        Toast.makeText(this, getString(strId1) + getString(str), Toast.LENGTH_SHORT).show()
    }

    override fun showToast(strId1: Int, strin2: String) {
        Toast.makeText(this, getString(strId1) + strin2, Toast.LENGTH_SHORT).show()
    }

    /**
     * 初始化状态页面
     */
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

    fun onErrorReplyClick() {

    }

    override fun hideStatusView() {
        if (mStatusView != null)
            mStatusView!!.hideStatusView()
    }

    /**
     * rxbus 接口的定义方法
     */
    fun rxBusOperate() {

    }

    override fun onStart() {
        super.onStart()
        UiCoreHelper.getProxyA().onActivityStart(this)
    }

    override fun onRestart() {
        super.onRestart()
        UiCoreHelper.getProxyA().onActivityRestart(this)
    }

    override fun onResume() {
        super.onResume()
        UiCoreHelper.getProxyA().onActivityResume(this)
    }

    override fun onPause() {
        super.onPause()
        UiCoreHelper.getProxyA().onActivityPause(this)
    }

    override fun onStop() {
        super.onStop()
        UiCoreHelper.getProxyA().onActivityStop(this)
    }

    override fun onDestroy() {
        hideSystemSoftInput()
        super.onDestroy()
        AppManager.getAppManager().finishActivity(this)
        UiCoreHelper.getProxyA().onActivityDestory(this)
    }

    /**
     * 重写此方法是为了Fragment的onActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UiCoreHelper.getProxyA().onActivityResult(this, requestCode, resultCode, data)
    }

    override fun onClick(v: View) {

    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_UP) {
            if (isDoubleClick) {
                return true
            }
        }
        //点击软键盘外部，软键盘消失
        if (ev.action == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            val v = currentFocus
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v!!.windowToken)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.height
            val right = left + v.width
            return if (event.x > left && event.x < right
                && event.y > top && event.y < bottom
            ) {
                // 点击EditText的事件，忽略它。
                false
            } else {
                true
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false
    }

    fun hideSystemSoftInput() {
        val view = window.peekDecorView()
        if (view != null && view.windowToken != null) {
            val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private fun hideSoftInput(token: IBinder?) {
        if (token != null) {
            val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(
                token,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    companion object {
        /**
         * 根布局中可以添加Fragment的container id
         */
        var FCID = R.id.fl_container
    }
}
