package com.lovelz.mykotlinproject.ui.holder.base

import android.content.Context
import android.databinding.ViewDataBinding
import android.view.View
import com.shuyu.commonrecycler.BindRecyclerBaseHolder

/**
 * @author lovelz
 * @date on 2018/12/4.
 */
abstract class DataBindingHolder<T, D>(context: Context, v: View, private val dataBing: ViewDataBinding) : BindRecyclerBaseHolder(context, v) {

    override fun createView(v: View) {

    }

    @Suppress("UNCHECKED_CAST")
    override fun onBind(model: Any, position: Int) {
        onBind(model as T, position, dataBing as D)
    }

    abstract fun onBind(model: T, position: Int, dataBing: D)

}