package com.lovelz.mykotlinproject.module.issue

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.lovelz.mykotlinproject.model.bean.CommentRequestModel
import com.lovelz.mykotlinproject.model.bean.Issue
import com.lovelz.mykotlinproject.model.ui.IssueUIModel
import com.lovelz.mykotlinproject.module.base.BaseViewModel
import com.lovelz.mykotlinproject.net.ResultCallBack
import com.lovelz.mykotlinproject.repository.IssueRepository
import javax.inject.Inject

/**
 * @author lovelz
 * @date on 2018/12/17.
 */
class IssueDetailViewModel @Inject constructor(private val issueRepository: IssueRepository, private val application: Application) : BaseViewModel(application) {

    var userName = ""

    var reposName = ""

    var issueNumber = 0

    val issueUIModel = IssueUIModel()

    val liveIssueModel = MutableLiveData<IssueUIModel>()

    override fun loadDataByRefresh() {
        loadInfo()
        loadData()
    }

    override fun loadDataByLoadMore() {
        loadData()
    }

    private fun loadInfo() {
        issueRepository.getIssueInfo(userName, reposName, issueNumber, object : ResultCallBack<IssueUIModel> {
            override fun onCacheSuccess(result: IssueUIModel?) {
                result?.apply {
                    issueUIModel.cloneFrom(this)
                }
            }

            override fun onSuccess(result: IssueUIModel?) {
                result?.apply {
                    issueUIModel.cloneFrom(this)
                    liveIssueModel.value = this
                }
            }

            override fun onFailure() {

            }
        })
    }

    private fun loadData() {
        issueRepository.getIssueComments(userName, reposName, issueNumber, page, this)
    }

    fun editIssue(context: Context, title: String, body: String) {
        val issue = Issue()
        issue.title = title
        issue.body = body
        issueRepository.editIssue(context, userName, reposName, issueNumber, issue, object : ResultCallBack<IssueUIModel> {
            override fun onSuccess(result: IssueUIModel?) {
                result?.apply {
                    issueUIModel.cloneFrom(this)
                    liveIssueModel.value = this
                }
            }
        })
    }

    fun commentIssue(context: Context, content: String, resultCallBack: ResultCallBack<IssueUIModel>?) {
        val commentRequestModel = CommentRequestModel()
        commentRequestModel.body = content
        issueRepository.commentIssue(context, userName, reposName, issueNumber, commentRequestModel, object : ResultCallBack<IssueUIModel> {
            override fun onSuccess(result: IssueUIModel?) {
                result?.apply {
                    val list = dataList.value
                    list?.add(this)
                    dataList.value = list
                }
                resultCallBack?.onSuccess(result)
            }

            override fun onFailure() {
                resultCallBack?.onFailure()
            }
        })
    }

    fun changeIssueStatus(context: Context, status: String) {
        val issue = Issue()
        issue.state = status
        issueRepository.editIssue(context, userName, reposName, issueNumber, issue, object : ResultCallBack<IssueUIModel> {
            override fun onSuccess(result: IssueUIModel?) {
                result?.apply {
                    issueUIModel.cloneFrom(this)
                    liveIssueModel.value = this
                }
            }
        })
    }

    fun lockIssueStatus(context: Context, lock: Boolean) {
        issueRepository.lockIssue(context, userName, reposName, issueNumber, lock, object : ResultCallBack<Boolean> {
            override fun onSuccess(result: Boolean?) {
                loadInfo()
            }
        })
    }

    fun editComment(context: Context, commentId: String, issueUIModel: IssueUIModel) {
        val commentRequestModel = CommentRequestModel()
        commentRequestModel.body = issueUIModel.action
        issueRepository.editComment(context, userName, reposName, commentId, commentRequestModel, object : ResultCallBack<IssueUIModel> {
            override fun onSuccess(result: IssueUIModel?) {
                result?.apply {
                    issueUIModel.cloneFrom(this)
                }
            }
        })
    }

    fun deleteComment(context: Context, commentId: String, resultCallBack: ResultCallBack<String>?) {
        issueRepository.deleteComment(context, userName, reposName, commentId, resultCallBack)
    }


}