package com.lovelz.mykotlinproject.utils

import android.content.Context
import com.lovelz.mykotlinproject.model.ui.EventUIAction
import com.lovelz.mykotlinproject.model.ui.EventUIModel
import com.lovelz.mykotlinproject.module.issue.IssueDetailActivity
import com.lovelz.mykotlinproject.module.person.PersonActivity

/**
 * 事件相关跳转
 *
 * @author lovelz
 * @date on 2018/12/8.
 */
object EventUtils {

    fun eventAction(context: Context?, eventUIModel: EventUIModel?) {
        when(eventUIModel?.actionType) {
            EventUIAction.Person -> {
                PersonActivity.gotoPersonInfo(eventUIModel.owner)
            }
            EventUIAction.Repos -> {

            }
            EventUIAction.Push -> {

            }
            EventUIAction.Issue -> {
                IssueDetailActivity.gotoIssueDetail(eventUIModel.owner, eventUIModel.repositoryName, eventUIModel.IssueNum)
            }
            EventUIAction.Release -> {

            }
        }
    }

}