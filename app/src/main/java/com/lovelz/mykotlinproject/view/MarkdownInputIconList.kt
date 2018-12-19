package com.lovelz.mykotlinproject.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.utils.dp
import com.mikepenz.iconics.view.IconicsTextView

/**
 * @author lovelz
 * @date on 2018/12/18.
 */
class MarkdownInputIconList : FrameLayout {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val listView = RecyclerView(context)
    val list: ArrayList<String> = arrayListOf("{GSY-MD_1}", "{GSY-MD_2}", "{GSY-MD_3}", "{GSY-MD_4}", "{GSY-MD_5}", "{GSY-MD_6}", "{GSY-MD_7}", "{GSY-MD_8}", "{GSY-MD_9}")
    var itemClick: AdapterView.OnItemClickListener? = null
    var editText: EditText? = null

    init {
        val listParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        listView.adapter = TextItemAdapter(list)
        listView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        addView(listView, listParams)

        itemClick = AdapterView.OnItemClickListener { parent, view, position, id ->
            var text = editText?.text ?: ""
            when(position) {
                0 -> {
                    text = "$text\n#"
                }
                1 -> {
                    text = "$text\n##"
                }
                2 -> {
                    text = "$text\n###"
                }
                3 -> {
                    text = "$text ** **"
                }
                4 -> {
                    text = "$text * *"
                }
                5 -> {
                    text = "$text `` "
                }
                6 -> {
                    text = "$text\n```\n\n```\n "
                }
                7 -> {
                    text = "$text []() "
                }
                8 -> {
                    text = "$text\n-\n"
                }
            }
            editText?.setText(text)
        }

    }

    inner class TextItemAdapter(private val dataList: ArrayList<String>) : RecyclerView.Adapter<TextItemAdapter.VH>() {

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val textView = IconicsTextView(context)
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            val res = resources
            val fontSize = res.getDimension(R.dimen.smallTextSize)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
            textView.setBackgroundResource(R.drawable.ripple_bg)

            textView.gravity = Gravity.CENTER
            textView.setPadding(10.dp)
            val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
            textView.layoutParams = lp

            return VH(textView)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.v.text = dataList[position]

            holder.v.setOnClickListener {
                itemClick?.onItemClick(null, it, position, 0)
            }
        }

        inner class VH(val v: TextView) : RecyclerView.ViewHolder(v)

    }

}