package com.lovelz.mykotlinproject.ui.holder

import android.content.Context
import android.databinding.ViewDataBinding
import android.view.View
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.databinding.LayoutUserItemBinding
import com.lovelz.mykotlinproject.model.ui.UserUIModel
import com.lovelz.mykotlinproject.ui.holder.base.DataBindingHolder

/**
 * @author lovelz
 * @date on 2018/12/11.
 */
class UserHolder(context: Context, private val view: View, dataBing: ViewDataBinding) : DataBindingHolder<UserUIModel, LayoutUserItemBinding>(context, view, dataBing) {

    override fun onBind(model: UserUIModel, position: Int, dataBing: LayoutUserItemBinding) {
        dataBing.userUIModel = model
    }

    companion object {
        const val ID = R.layout.layout_user_item
    }

}