package com.lovelz.mykotlinproject.utils

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.ui.adapter.TextListAdapter
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.OnItemClickListener
import com.orhanobut.dialogplus.ViewHolder
import kotlinx.android.synthetic.main.layout_issue_edit_dialog.view.*
import org.jetbrains.anko.toast

/**
 * 扩展Context显示的Dialog
 *
 * @author lovelz
 * @date on 2018/12/18.
 */
fun Context.showIssueEditDialog(title: String, needEditTitle: Boolean, editTitle: String?, editContent: String?, listener: IssueDialogClickListener?) {
    val contentView = LayoutInflater.from(this).inflate(R.layout.layout_issue_edit_dialog, null, false)
    val contentHeight = (Resources.getSystem().displayMetrics.heightPixels * 0.6).toInt()
    contentView.issue_dialog_content_layout.layoutParams.height = contentHeight
    contentView.issue_dialog_markdown_list.editText = contentView.issue_dialog_edit_content

    val dialog = DialogPlus.newDialog(this)
            .setContentHolder(ViewHolder(contentView))
            .setCancelable(false)
            .setContentBackgroundResource(R.color.transparent)
            .setGravity(Gravity.CENTER)
            .setMargin(30.dp, 0, 30.dp, 0)
            .create()

    contentView.issue_dialog_edit_title.visibility = if (needEditTitle) {
        View.VISIBLE
    } else {
        View.GONE
    }

    contentView.issue_dialog_title.text = title
    editTitle?.apply {
        contentView.issue_dialog_edit_title.setText(this)
    }
    editContent?.apply {
        contentView.issue_dialog_edit_content.setText(this)
    }

    //确定
    contentView.issue_dialog_edit_ok.setOnClickListener {
        val titleText = contentView.issue_dialog_edit_title.text?.toString()
        val contentText = contentView.issue_dialog_edit_content.text?.toString()
        if (needEditTitle && titleText.isNullOrBlank()) {
            toast(R.string.issueTitleEmpty)
            return@setOnClickListener
        }
        if (contentText.isNullOrBlank()) {
            toast(R.string.issueContentEmpty)
            return@setOnClickListener
        }
        listener?.onConfirm(dialog, title, titleText, contentText)
    }

    //取消
    contentView.issue_dialog_edit_cancel.setOnClickListener {
        dialog.dismiss()
    }

    dialog.show()
}

/**
 * 显示选择弹出框
 */
fun Context.showOptionSelectDialog(dataList: ArrayList<String>, itemClickListener: OnItemClickListener) {
    val dialog = DialogPlus.newDialog(this)
            .setAdapter(TextListAdapter(this, dataList))
            .setGravity(Gravity.CENTER)
            .setOnItemClickListener(itemClickListener)
            .setExpanded(false)
            .create()
    dialog.show()
}

interface IssueDialogClickListener {
    fun onConfirm(dialog: DialogPlus, title: String, editTitle: String?, editContent: String?)
}
