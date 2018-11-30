package com.lovelz.mykotlinproject.net

import android.accounts.NetworkErrorException
import com.lovelz.mykotlinproject.utils.GsonUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * @author lovelz
 * @date on 2018/11/24.
 */
abstract class ResultObserver <T> : Observer<Response<T>> {

    override fun onSubscribe(d: Disposable) {
        if (!d.isDisposed) {
            onRequestStart()
        }
    }

    override fun onNext(response: Response<T>) {
        onPageInfo(response)
        onRequestEnd()

        if (response.isSuccessful) {
            try {
                onSuccess(response.body())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            try {
                onInnerCodeError(response.code(), response.message())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onError(e: Throwable) {
        onRequestEnd()

        try {
            if (e is ConnectException
                    || e is TimeoutException
                    || e is NetworkErrorException
                    || e is UnknownHostException) {
                onFailure(e, true)
            } else{
                onFailure(e, false)
            }
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }

    override fun onComplete() {

    }

    /**
     * 返回成功
     *
     * @param result
     * @throws Exception
     */
    @Throws(Exception::class)
    abstract fun onSuccess(result: T?)

    /**
     * 返回错误
     *
     * @param e Throwable
     * @param isNetworkError Boolean 是否为网络错误
     * @throws Exception
     */
    @Throws(Exception::class)
    abstract fun onFailure(e: Throwable, isNetworkError: Boolean)

    /**
     * 返回成功，但是code错误
     *
     * @param code Int
     * @param message String
     * @throws Exception
     */
    @Throws(Exception::class)
    open fun onCodeError(code: Int, message: String) {

    }

    private fun onPageInfo(response: Response<T>) {
        val pageString = response.headers().get("page_info")
        pageString?.let {
            val pageInfo = GsonUtils.parseJsonToBean(it, PageInfoInterceptor.PageInfo::class.java)
            onPageInfo(pageInfo.first, pageInfo.next - 1, pageInfo.last)
        }
    }

    open fun onPageInfo(first: Int, current: Int, last: Int) {

    }

    open fun onInnerCodeError(code: Int, message: String) {
        onCodeError(code, message)
    }

    open fun onRequestStart() {

    }

    open fun onRequestEnd() {

    }

}