package com.lovelz.mykotlinproject.ui.holder.base

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.sprite.SpriteContainer
import com.github.ybq.android.spinkit.style.ThreeBounce
import com.lovelz.mykotlinproject.R
import com.shuyu.commonrecycler.xrecycler.base.BaseLoadMoreFooter
import org.jetbrains.anko.textColor

/**
 * 加载更多自定义
 *
 * @author lovelz
 * @date on 2018/12/5.
 */
class BindCustomLoadMoreFooter : BaseLoadMoreFooter {

    private var mImageView: SpinKitView? = null

    private var mText: TextView? = null

    private var spriteContainer: SpriteContainer? = null

    private var prevState = BaseLoadMoreFooter.STATE_LOADING

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    override fun setProgressStyle(style: Int) {}

    override fun setState(state: Int) {
        //处理各状态
        when(state) {
            BaseLoadMoreFooter.STATE_LOADING -> {
                mImageView?.visibility = View.VISIBLE
                mText?.text = context.getString(R.string.listview_loading)
                this.visibility = View.VISIBLE
                spriteContainer?.start()
            }
            BaseLoadMoreFooter.STATE_COMPLETE -> {
                if (prevState == BaseLoadMoreFooter.STATE_NOMORE) {
                    return
                }
                mText?.text = context.getString(R.string.listview_loading)
                this.visibility = View.GONE
                spriteContainer?.stop()
            }
            BaseLoadMoreFooter.STATE_NOMORE -> {
                mImageView?.visibility = View.GONE
                mText?.text = context.getString(R.string.nomore_loading)
                this.visibility = View.VISIBLE
                spriteContainer?.stop()
            }
        }
        prevState = state
    }

    private fun initView() {
        gravity = Gravity.CENTER
        layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setPadding(0, resources.getDimension(R.dimen.textandiconmargin).toInt(), 0,
                3 * resources.getDimension(R.dimen.textandiconmargin).toInt())

        mImageView = SpinKitView(context)
        mImageView?.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mImageView?.setColor(ContextCompat.getColor(context, R.color.colorPrimary))

        //设置动画
        val animator = ThreeBounce()
        mImageView?.setIndeterminateDrawable(animator)

        addView(mImageView)

        mText = TextView(context)
        mText?.text = context.getString(R.string.listview_loading)
        mText?.textColor = ContextCompat.getColor(context, R.color.colorPrimary)
        val res = resources
        val fontSize = res.getDimension(R.dimen.smallTextSize)
        mText?.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(res.getDimension(R.dimen.textandiconmargin).toInt(), 0, 0, 0)
        mText?.layoutParams = layoutParams
        addView(mText)

        spriteContainer = animator

    }

}