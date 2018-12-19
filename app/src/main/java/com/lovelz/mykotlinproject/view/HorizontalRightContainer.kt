package com.lovelz.mykotlinproject.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.utils.dp
import com.mikepenz.iconics.view.IconicsTextView

/**
 * 横向距右布局
 *
 * @author lovelz
 * @date on 2018/12/18.
 */
class HorizontalRightContainer : CardView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val listView = RecyclerView(context)
    val list: ArrayList<String> = arrayListOf()
    var itemClick: AdapterView.OnItemClickListener? = null

    init {
        setContentPadding(10.dp, 5.dp, 10.dp, 5.dp)

        val listParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        listView.adapter = TextItemAdapter(list)
        listView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)

        addView(listView, listParams)
    }

    inner class TextItemAdapter(private val dataList: ArrayList<String>) : RecyclerView.Adapter<TextItemAdapter.VH>() {

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val textView = IconicsTextView(context)
            textView.setPadding(15.dp)
            textView.setBackgroundResource(R.drawable.ripple_bg)
            textView.gravity = Gravity.CENTER

            val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
            textView.layoutParams = lp
            return VH(textView)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.v.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            val res = resources
            val fontSize = res.getDimension(R.dimen.smallTextSize)
            holder.v.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
            holder.v.text = dataList[position]

            holder.v.setOnClickListener {
                itemClick?.onItemClick(null, it, position, 0)
            }
        }

        inner class VH(val v: TextView) : RecyclerView.ViewHolder(v)
    }
}