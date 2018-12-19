package com.lovelz.mykotlinproject.view

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import devlight.io.library.ntb.NavigationTabBar

/**
 * 具有双击回调的{@link devlight.io.library.ntb.NavigationTabBar}
 *
 * @author lovelz
 * @date on 2018/12/3.
 */
class DoubleNavigationTabBar : NavigationTabBar {

    var isTouchEnable = true

    var tabDoubleClickListener: TabDoubleClickListener? = null

    var gestureDetector = GestureDetector(context.applicationContext, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent?): Boolean {
            tabDoubleClickListener?.onDoubleClick(mIndex)
            return super.onDoubleTap(e)
        }
    })

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isTouchEnable) {
            return true
        }

        super.onTouchEvent(event)

        gestureDetector.onTouchEvent(event)

        return true
    }

    interface TabDoubleClickListener {
        fun onDoubleClick(position: Int)
    }
}