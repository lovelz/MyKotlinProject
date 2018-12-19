package com.lovelz.mykotlinproject.model.ui

/**
 * @author lovelz
 * @date on 2018/12/5.
 */
data class EventUIModel(var username: String = "",
                        var image: String = "",
                        var action: String = "",
                        var des: String = "",
                        var time: String = "---",
                        var actionType: EventUIAction = EventUIAction.Person,
                        var owner: String = "",
                        var repositoryName: String = "",
                        var IssueNum: Int = 0,
                        var releaseUrl: String = "",
                        var pushSha: ArrayList<String> = arrayListOf(),
                        var pushShaDes: ArrayList<String> = arrayListOf(),
                        var threadId: String = "")

enum class EventUIAction {
    Person,
    Repos,
    Push,
    Release,
    Issue
}