package com.lovelz.mykotlinproject.ui.holder

import android.content.Context
import android.databinding.ViewDataBinding
import android.view.View
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.databinding.LayoutIssueCommentItemBinding
import com.lovelz.mykotlinproject.model.ui.IssueUIModel
import com.lovelz.mykotlinproject.module.person.PersonActivity
import com.lovelz.mykotlinproject.ui.holder.base.DataBindingHolder
import kotlinx.android.synthetic.main.layout_issue_comment_item.view.*

/**
 * Issue评论item
 *
 * @author lovelz
 * @date on 2018/12/18.
 */
class IssueCommentHolder(context: Context, private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<IssueUIModel, LayoutIssueCommentItemBinding>(context, v, dataBing){

    override fun createView(v: View) {
        super.createView(v)
    }

    override fun onBind(model: IssueUIModel, position: Int, dataBing: LayoutIssueCommentItemBinding) {
        dataBing.issueUIModel = model
        v.issue_user_img.setOnClickListener {
            PersonActivity.gotoPersonInfo(model.username)
        }
    }

    companion object {
        const val ID = R.layout.layout_issue_comment_item
    }

}