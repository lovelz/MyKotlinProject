package com.lovelz.mykotlinproject.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebView

/**
 * @author lovelz
 * @date on 2018/12/11.
 */
class TouchWebView : WebView {

    private var startX: Float = 0.0f
    private var startY: Float = 0.0f
    private var offsetX: Float = 0.0f
    private var offsetY: Float = 0.0f

    var requestIntercept = true

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(requestIntercept)
                startX = event.x
                startY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                offsetX = Math.abs(event.x - startX)
                offsetY = Math.abs(event.y - startY)
                if (offsetX > offsetY) {
                    parent.requestDisallowInterceptTouchEvent(requestIntercept)
                } else {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (!isVerticalScrollBarEnabled) {
            //禁止上下滚动
            scrollTo(l, 0)
        } else if (!isHorizontalScrollBarEnabled) {
            //禁止左右滚动
            scrollTo(0, t)
        }
    }

}