package com.lovelz.mykotlinproject.model.conversion

import com.lovelz.mykotlinproject.model.bean.Issue
import com.lovelz.mykotlinproject.model.bean.IssueEvent
import com.lovelz.mykotlinproject.model.ui.IssueUIModel
import com.lovelz.mykotlinproject.utils.CommonUtils

/**
 * Issue相关实体转换
 *
 * @author lovelz
 * @date on 2018/12/17.
 */
object IssueConversion {

    fun issueToIssueUIModel(issue: Issue): IssueUIModel {
        val issueUIModel = IssueUIModel()
        issueUIModel.username = issue.user?.login ?: ""
        issueUIModel.image = issue.user?.avatarUrl ?: ""
        issueUIModel.action = issue.title ?: ""
        issueUIModel.time = CommonUtils.getDateStr(issue.createdAt)
        issueUIModel.comment = issue.commentNum.toString()
        issueUIModel.issueNum = issue.number
        issueUIModel.status = issue.state ?: ""
        issueUIModel.content = issue.body ?: ""
        issueUIModel.locked = issue.locked
        return issueUIModel
    }

    fun issueEventToIssueUIModel(issueEvent: IssueEvent): IssueUIModel {
        val issueUIModel = IssueUIModel()
        issueUIModel.username = issueEvent.user?.login ?: ""
        issueUIModel.image = issueEvent.user?.avatarUrl ?: ""
        issueUIModel.action = issueEvent.body ?: ""
        issueUIModel.time = CommonUtils.getDateStr(issueEvent.createdAt)
        issueUIModel.status = issueEvent.id ?: ""
        return issueUIModel
    }

}