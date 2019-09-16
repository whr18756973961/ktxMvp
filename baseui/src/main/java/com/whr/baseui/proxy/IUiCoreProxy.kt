package com.whr.baseui.proxy

import android.app.Activity
import android.content.Context
import android.content.Intent

import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by whr on 2018/4/16.
 * BaseUi代理类,采用代理模式，用于在上层model中设置baseui参数
 */

abstract class IUiCoreProxy {
    /**
     * 获取登录状态
     */
    var isLogin = false
        private set

    /**
     * 全局context对象
     *
     * @return
     */
    abstract val context: Context

    /**
     * 配置主题颜色
     *
     * @return
     */
    @ColorRes
    abstract fun colorPrimary(): Int

    /**
     * 配置主题颜色
     *
     * @return
     */
    @ColorRes
    abstract fun colorPrimaryDark(): Int

    /**
     * glide加载图片的时候
     *
     * @return
     */
    abstract fun glidePlaceholderRes(): Int

    /**
     * 配置主题颜色
     *
     * @return
     */
    @ColorRes
    abstract fun colorAccent(): Int

    /**
     * 全局配置等待对话框的布局文件
     *
     * @return
     */
    abstract fun waitDialogRes(): Int

    /**
     * 配置下拉刷新空间主题色
     *
     * @return
     */
    abstract fun colorSchemeResources(): IntArray

    /**
     * 配置下拉滚动状态
     *
     * @param recyclerView
     * @param newState
     */
    abstract fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int)

    /**
     * 全局配置头部的文件
     *
     * @return
     */
    abstract fun headerIdRes(): Int

    /**
     * 返回按鈕的名稱
     *
     * @return
     */
    abstract fun headerBackId(): Int

    /**
     * 標題的名稱
     *
     * @return
     */
    abstract fun headerTitleId(): Int

    /**
     * 右側的名稱
     *
     * @return
     */
    abstract fun headerRightId(): Int

    /**
     * 右側的名稱
     *
     * @return
     */
    abstract fun headerRightIconId(): Int

    abstract fun headerBtmLineId(): Int

    /**
     * 配置登陆的fragment
     *
     * @return
     */
    abstract fun loginFragment(): Class<out Fragment>

    /**
     * 设置是否登录成功了
     *
     * @param isLogin
     */
    fun login(isLogin: Boolean) {
        this.isLogin = isLogin
    }

    /**
     * Activity创建时被调用
     *
     * @param activity
     */
    fun onActivityCreate(activity: Activity) {

    }

    /**
     * Activity创建或者从后台重新回到前台时被调用
     *
     * @param activity
     */
    fun onActivityStart(activity: Activity) {}

    /**
     * Activity从后台重新回到前台时被调用
     *
     * @param activity
     */
    fun onActivityRestart(activity: Activity) {}

    /**
     * Activity创建或者从被覆盖、后台重新回到前台时被调用
     *
     * @param activity
     */
    fun onActivityResume(activity: Activity) {}

    /**
     * Activity被覆盖到下面或者锁屏时被调用
     *
     * @param activity
     */
    fun onActivityPause(activity: Activity) {}

    /**
     * 退出当前Activity或者跳转到新Activity时被调用
     *
     * @param activity
     */
    fun onActivityStop(activity: Activity) {}


    /**
     * 退出当前Activity时被调用,调用之后Activity就结束了
     *
     * @param activity
     */
    fun onActivityDestory(activity: Activity) {}

    /**
     * Activity onActivityResult
     *
     * @param activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    fun onActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {}

    /**
     * 当一个Fragment对象关联到一个Activity时调用
     *
     * @param fragment
     */
    fun onFragmentAttach(fragment: Fragment) {}

    /**
     * 这个方法是在fragment初始化的时候调用，我们通常在这个方法中使用getArgument获取activity传来的初始化fragment的参数
     *
     * @param fragment
     */
    fun onFragmentCreate(fragment: Fragment) {}

    /**
     * 创建与Fragment对象关联的View视图时调用
     *
     * @param fragment
     */
    fun onFragmentCreateView(fragment: Fragment) {}

    /**
     * 当Activity对象完成自己的onCreate方法时调用
     *
     * @param fragment
     */
    fun onFragmentActivityCreated(fragment: Fragment) {}

    /**
     * Fragment对象在ui可见时调用
     *
     * @param fragment
     */
    fun onFragmentStart(fragment: Fragment) {}

    /**
     * Fragment对象的ui可以与用户交互时调用
     *
     * @param fragment
     */
    fun onFragmentResume(fragment: Fragment) {}

    /**
     * Fragment对象可见，但不可交互。有Activity对象转为onPause状态时调用
     *
     * @param fragment
     */
    fun onFragmentPause(fragment: Fragment) {}

    /**
     * 有空间完全遮挡；或者宿主Activity对象转为onStop状态时调用
     *
     * @param fragment
     */
    fun onFragmentStop(fragment: Fragment) {}

    /**
     * Fragment对象清理view资源时调用，也就是移除fragment中的视图
     *
     * @param fragment
     */
    fun onFragmentDestroyView(fragment: Fragment) {}

    /**
     * Fragment对象完成对象清理View资源时调用
     *
     * @param fragment
     */
    fun onFragmentDestroy(fragment: Fragment) {}

    /**
     * Fragment对象没有与Activity对象关联时调用
     *
     * @param fragment
     */
    fun onFragmentDetach(fragment: Fragment) {}

}
