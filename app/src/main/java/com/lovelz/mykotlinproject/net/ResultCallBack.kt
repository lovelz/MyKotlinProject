package com.lovelz.mykotlinproject.net

/**
 * @author lovelz
 * @date on 2018/12/3.
 */
interface ResultCallBack<T> {

    fun onPage(first: Int, current: Int, last: Int) {

    }

    fun onSuccess(result: T?)

    fun onCacheSuccess(result: T?) {

    }

    fun onFailure() {

    }

}