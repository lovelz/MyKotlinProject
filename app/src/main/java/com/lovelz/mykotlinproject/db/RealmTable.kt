package com.lovelz.mykotlinproject.db

import io.realm.RealmObject
import io.realm.annotations.RealmClass

/**
 * @author lovelz
 * @date on 2018/12/1.
 */

/**
 * 用户表
 */
@RealmClass
open class UserInfo : RealmObject() {
    open var userName: String? = null
    open var data: String? = null
}

/**
 * 用户事件表
 */
@RealmClass
open class ReceivedEvent : RealmObject() {
    open var data: String? = null
}

/**
 * 用户关注表
 */
@RealmClass
open class OrgMember : RealmObject() {
    open var org: String? = null
    open var data: String? = null
}

/**
 * 用户动态表
 */
@RealmClass
open class UserEvent : RealmObject() {
    open var userName: String? = null
    open var data: String? = null
}

@RealmClass
open class IssueDetail : RealmObject() {
    open var fullName: String? = null
    open var number: String? = null
    open var data: String? = null
}

@RealmClass
open class IssueComment : RealmObject() {
    open var fullName: String? = null
    open var number: String? = null
    open var commentId: String? = null
    open var data: String? = null
}