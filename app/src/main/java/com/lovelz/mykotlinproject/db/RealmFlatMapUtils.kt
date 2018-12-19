package com.lovelz.mykotlinproject.db

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmQuery
import io.realm.RealmResults
import retrofit2.Response

/**
 * @author lovelz
 * @date on 2018/12/1.
 */

/**
 * 保存response中的实体信息
 *
 * @param T
 * @param E : RealmModel
 * @property clazz Class<E>
 * @property listener FlatTransactionInterface<E>
 * @constructor
 */
class FlatMapRealmSaveResult<T, E : RealmModel>(response: Response<T>, private val clazz: Class<E>,
                                                private val listener: FlatTransactionInterface<E>,
                                                needSave: Boolean) {
    init {
        if (response.isSuccessful && needSave) {
            val realm = Realm.getDefaultInstance()
            realm.executeTransaction { bgRealm ->
                val results = listener.query(bgRealm.where(clazz))
                val commitTarget = if (results.isNotEmpty()) {
                    results[0]
                } else {
                    bgRealm.createObject(clazz)
                }
                listener.onTransaction(commitTarget)
            }
        }
    }

}

interface FlatTransactionInterface<E: RealmModel> {
    fun query(q: RealmQuery<E>): RealmResults<E>
    fun onTransaction(targetObject: E?)
}

/**
 * 获取数据库保存的列表信息
 * @param realm Realm
 * @param listener FlatRealmReadConversionInterface<T, E>
 * @return ArrayList<Any>
 */
fun <T, E: RealmModel> FlatMapRealmReadList(realm: Realm, listener: FlatRealmReadConversionInterface<T, E>): ArrayList<Any> {
    val realmResults = listener.query(realm)
    val list = if (realmResults.isEmpty()) {
        ArrayList()
    } else {
        listener.onJSON(realmResults[0]!!)
    }
    val dataList = ArrayList<Any>()
    for (item in list) {
        dataList.add(listener.onConversion(item))
    }
    return dataList
}

/**
 * 获取数据库保存的实体信息
 */
fun <T, E : RealmModel, R> FlatMapRealmReadObject(realm: Realm, listener: FlatRealmReadConversionObjectInterface<T, E, R>): R? {
    val realmResults = listener.query(realm)
    val data = if (realmResults.isEmpty()) {
        null
    } else {
        listener.onJson(realmResults[0]!!)
    }
    return listener.onConversion(data)
}

interface FlatRealmReadConversionInterface<T, E> {
    fun query(realm: Realm): RealmResults<E>
    fun onJSON(t: E): List<T>
    fun onConversion(t: T): Any
}

interface FlatRealmReadConversionObjectInterface<T, E, R> {
    fun query(realm: Realm): RealmResults<E>
    fun onJson(t: E): T
    fun onConversion(t: T?): R?
}