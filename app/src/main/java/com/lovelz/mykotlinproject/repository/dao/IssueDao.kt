package com.lovelz.mykotlinproject.repository.dao

import android.app.Application
import com.lovelz.mykotlinproject.db.*
import com.lovelz.mykotlinproject.model.bean.Issue
import com.lovelz.mykotlinproject.model.bean.IssueEvent
import com.lovelz.mykotlinproject.model.conversion.IssueConversion
import com.lovelz.mykotlinproject.model.ui.IssueUIModel
import com.lovelz.mykotlinproject.utils.GsonUtils
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import retrofit2.Response
import javax.inject.Inject

/**
 * Issue相关数据库操作
 *
 * @author lovelz
 * @date on 2018/12/17.
 */
class IssueDao @Inject constructor(private val application: Application) {

    /**
     * 保存Issue详情
     */
    fun saveIssueInfoDao(response: Response<Issue>, userName: String, reposName: String, number: Int) {
        FlatMapRealmSaveResult(response, IssueDetail::class.java, object : FlatTransactionInterface<IssueDetail> {
            override fun query(q: RealmQuery<IssueDetail>): RealmResults<IssueDetail> {
                return q.equalTo("fullName", "$userName/$reposName").equalTo("number", number.toString()).findAll()
            }

            override fun onTransaction(targetObject: IssueDetail?) {
                val data = GsonUtils.jsonToString(response.body())
                targetObject?.fullName = "$userName/$reposName"
                targetObject?.number = number.toString()
                targetObject?.data = data
            }
        }, true)
    }

    /**
     * 获取Issue详情
     */
    fun getIssueInfoDao(userName: String, reposName: String, number: Int): Observable<IssueUIModel?> {
        return RealmFactory.getRealmObservable()
                .map {
                    val item = FlatMapRealmReadObject(it, object : FlatRealmReadConversionObjectInterface<Issue, IssueDetail, IssueUIModel> {
                        override fun query(realm: Realm): RealmResults<IssueDetail> {
                            return realm.where(IssueDetail::class.java).equalTo("fullName", "$userName/$reposName").equalTo("number", number.toString()).findAll()
                        }

                        override fun onJson(t: IssueDetail): Issue {
                            return GsonUtils.parseJsonToBean(t.data!!, Issue::class.java)
                        }

                        override fun onConversion(t: Issue?): IssueUIModel? {
                            return if (t == null) {
                                IssueUIModel()
                            } else {
                                IssueConversion.issueToIssueUIModel(t)
                            }
                        }
                    })
                    item
                }
    }

    /**
     * 保存Issue评论
     */
    fun saveIssueCommentDao(response: Response<ArrayList<IssueEvent>>, userName: String, reposName: String, number: Int, needSave: Boolean) {
        FlatMapRealmSaveResult(response, IssueComment::class.java, object : FlatTransactionInterface<IssueComment> {
            override fun query(q: RealmQuery<IssueComment>): RealmResults<IssueComment> {
                return q.equalTo("fullName", "$userName/$reposName").equalTo("number", number.toString()).findAll()
            }

            override fun onTransaction(targetObject: IssueComment?) {
                val data = GsonUtils.jsonToString(response.body())
                targetObject?.fullName = "$userName/$reposName"
                targetObject?.commentId = "-1"
                targetObject?.number = number.toString()
                targetObject?.data = data
            }

        }, needSave)
    }

    /**
     * 获取Issue评论
     */
    fun getIssueCommentDao(userName: String, reposName: String, number: Int): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
                .map {
                    val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<IssueEvent, IssueComment> {
                        override fun query(realm: Realm): RealmResults<IssueComment> {
                            return realm.where(IssueComment::class.java).equalTo("fullName", "$userName/$realm").equalTo("number", number.toString()).findAll()
                        }

                        override fun onJSON(t: IssueComment): List<IssueEvent> {
                            return GsonUtils.parseJsonToArrayBeans(t.data!!, IssueEvent::class.java)
                        }

                        override fun onConversion(t: IssueEvent): Any {
                            return IssueConversion.issueEventToIssueUIModel(t)
                        }

                    })
                    list
                }
    }

}