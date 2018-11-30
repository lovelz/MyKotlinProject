package com.lovelz.mykotlinproject.db

import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * @author lovelz
 * @date on 2018/11/26.
 */
class RealmFactory private constructor() {

    companion object {
        private const val VERSION = 1L

        @Volatile
        private var mRealmFactory: RealmFactory? = null

        val instance: RealmFactory
            get() {
                if (mRealmFactory == null) {
                    synchronized(RealmFactory::class.java) {
                        if (mRealmFactory == null) {
                            mRealmFactory = RealmFactory()
                        }
                    }
                }
                return mRealmFactory!!
            }

        fun getRealmObservable(): Observable<Realm> {
            return Observable.create { emitter ->
                val observableRealm = Realm.getDefaultInstance()
                emitter.onNext(observableRealm)
                emitter.onComplete()
            }
        }
    }

    init {
        val config = RealmConfiguration.Builder()
                .name("lz.realm")
                .schemaVersion(VERSION)
                .build()
        Realm.setDefaultConfiguration(config)
    }

}