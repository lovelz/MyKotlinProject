package com.lovelz.mykotlinproject.ui.holder

import android.content.Context
import android.databinding.ViewDataBinding
import android.view.View
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.databinding.LayoutEventItemBinding
import com.lovelz.mykotlinproject.model.ui.EventUIModel
import com.lovelz.mykotlinproject.module.person.PersonActivity
import com.lovelz.mykotlinproject.ui.holder.base.DataBindingHolder
import kotlinx.android.synthetic.main.layout_event_item.view.*

/**
 * 事件item
 *
 * @author lovelz
 * @date on 2018/12/5.
 */
class EventHolder(context: Context, private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<EventUIModel, LayoutEventItemBinding>(context, v, dataBing) {

    override fun createView(v: View) {
        super.createView(v)
    }

    override fun onBind(model: EventUIModel, position: Int, dataBing: LayoutEventItemBinding) {
        dataBing.eventUIModel = model
        v.event_user_img.setOnClickListener {
            PersonActivity.gotoPersonInfo(model.username)
        }
    }

    companion object {
        const val ID = R.layout.layout_event_item
    }


}