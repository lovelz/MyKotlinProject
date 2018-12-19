package com.lovelz.mykotlinproject.ui.holder

import android.content.Context
import android.databinding.ViewDataBinding
import android.view.View
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.databinding.LayoutEmptyBinding
import com.lovelz.mykotlinproject.model.ui.EmptyUIModel
import com.lovelz.mykotlinproject.ui.holder.base.DataBindingHolder

/**
 * @author lovelz
 * @date on 2018/11/27.
 */
class EmptyHolder(context: Context, private val v: View, dataBing: ViewDataBinding) :
        DataBindingHolder<EmptyUIModel, LayoutEmptyBinding>(context, v, dataBing) {

    override fun createView(v: View) {
        super.createView(v)
    }

    override fun onBind(model: EmptyUIModel, position: Int, dataBing: LayoutEmptyBinding) {

    }

    companion object {
        const val ID = R.layout.layout_empty
    }

}