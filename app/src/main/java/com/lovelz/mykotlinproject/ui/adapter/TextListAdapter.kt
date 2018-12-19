package com.lovelz.mykotlinproject.ui.adapter

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.utils.dp
import androidx.core.view.setPadding

/**
 * @author lovelz
 * @date on 2018/12/18.
 */
class TextListAdapter(private val context: Context, private val dataList: ArrayList<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolder
        var view: View? = convertView

        if (view == null) {
            view = TextView(context)
            view.setPadding(10.dp)
            view.gravity = Gravity.CENTER
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        val fontSize = context.resources.getDimension(R.dimen.normalTextSize)
        viewHolder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)

        viewHolder.textView.text = dataList[position]

        return view
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    data class ViewHolder(val textView: TextView)


}