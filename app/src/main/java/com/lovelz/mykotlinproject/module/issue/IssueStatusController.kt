package com.lovelz.mykotlinproject.module.issue

import android.content.Context
import android.widget.AdapterView
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.model.ui.IssueUIModel
import com.lovelz.mykotlinproject.net.ResultCallBack
import com.lovelz.mykotlinproject.utils.IssueDialogClickListener
import com.lovelz.mykotlinproject.utils.showIssueEditDialog
import com.lovelz.mykotlinproject.view.HorizontalRightContainer
import com.orhanobut.dialogplus.DialogPlus
import com.shuyu.commonrecycler.BindSuperAdapter

/**
 * Issue 底部操作相关
 *
 * @author lovelz
 * @date on 2018/12/18.
 */
class IssueStatusController(private val context: Context, private val adapter: BindSuperAdapter?, private val container: HorizontalRightContainer,
                            private val issueDetailFragment: IssueDetailFragment) : IssueDialogClickListener {

    override fun onConfirm(dialog: DialogPlus, title: String, editTitle: String?, editContent: String?) {
        when {
            title.contains(context.getString(R.string.issueComment)) -> {
                issueDetailFragment.getViewModel().commentIssue(context, editContent!!, object : ResultCallBack<IssueUIModel> {
                    override fun onSuccess(result: IssueUIModel?) {
                        result?.apply {
                            dialog.dismiss()
                            issueDetailFragment.getRecyclerView()?.layoutManager?.scrollToPosition(adapter?.dataList!!.size - 1)
                        }
                    }
                })
            }

            title.contains(context.getString(R.string.issueEdit)) -> {
                issueDetailFragment.getViewModel().editIssue(context, editTitle!!, editContent!!)
            }
        }
    }

    fun initContainer(issueUIModel: IssueUIModel?) {
        if (issueUIModel == null) return

        val dataList = getContainerList(issueUIModel)
        val issueInfo = issueDetailFragment.getViewModel().issueUIModel

        container.list.clear()
        container.list.addAll(dataList)
        container.listView.adapter.notifyDataSetChanged()

        container.itemClick = AdapterView.OnItemClickListener { _, _, position, _ ->
            val item = container.list[position]
            when {
                item.contains(context.getString(R.string.issueComment)) -> {
                    context.showIssueEditDialog(context.getString(R.string.issueComment), false, "", "", this)
                }
                item.contains(context.getString(R.string.issueEdit)) -> {
                    context.showIssueEditDialog(context.getString(R.string.issueComment), true, issueInfo.action, issueInfo.content, this)
                }
                item.contains(context.getString(R.string.issueClose)) -> {
                    issueDetailFragment.getViewModel().changeIssueStatus(context, "closed")
                }
                item.contains(context.getString(R.string.issueOpen)) -> {
                    issueDetailFragment.getViewModel().changeIssueStatus(context, "open")
                }
                item.contains(context.getString(R.string.issueUnlock)) -> {
                    issueDetailFragment.getViewModel().lockIssueStatus(context, false)
                }
                item.contains(context.getString(R.string.issueLocked)) -> {
                    issueDetailFragment.getViewModel().lockIssueStatus(context, true)
                }
            }
        }
    }

    private fun getContainerList(issueUIModel: IssueUIModel): ArrayList<String> {
        val list = arrayListOf(context.getString(R.string.issueComment), context.getString(R.string.issueEdit))

        val status = if (issueUIModel.status == "open") {
            context.getString(R.string.issueClose)
        } else {
            context.getString(R.string.issueOpen)
        }

        val lock = if (issueUIModel.locked) {
            context.getString(R.string.issueUnlock)
        } else {
            context.getString(R.string.issueLocked)
        }

        list.add(status)
        list.add(lock)
        return list
    }

}