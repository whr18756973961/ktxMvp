package com.whr.baseui.swipeback

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity


/**
 * SwipeBackActivity
 * Created by whr on 18/9/13.
 */
open class SwipeBackActivity : AppCompatActivity() {
    var swipeBackLayout: SwipeBackLayout? = null
        private set
    /**
     * 当Fragment根布局 没有 设定background属性时,
     * 库默认使用Theme的android:windowbackground作为Fragment的背景,
     * 如果不像使用windowbackground作为背景, 可以通过该方法改变Fragment背景。
     */
    var defaultFragmentBackground = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onActivityCreate()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        swipeBackLayout!!.attachToActivity(this)
    }

    internal fun onActivityCreate() {
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.decorView.setBackgroundDrawable(null)
        swipeBackLayout = SwipeBackLayout(this)
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        swipeBackLayout!!.layoutParams = params
    }

    fun setSwipeBackEnable(enable: Boolean) {
        swipeBackLayout!!.setEnableGesture(enable)
    }

    /**
     * 限制SwipeBack的条件,默认栈内Fragment数 <= 1时 , 优先滑动退出Activity , 而不是Fragment
     *
     * @return true: Activity可以滑动退出, 并且总是优先; false: Activity不允许滑动退出
     */
    open fun swipeBackPriority(): Boolean {
        return supportFragmentManager.backStackEntryCount <= 1
    }
}
