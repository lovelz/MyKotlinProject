package com.lovelz.mykotlinproject.net

import android.accounts.NetworkErrorException
import android.content.Context
import com.lovelz.mykotlinproject.R
import org.jetbrains.anko.toast
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * 返回网络访问错误提示信息
 *
 * @author lovelz
 * @date on 2018/12/1.
 */
abstract class ResultTipObserver<T>(private val context: Context) : ResultObserver<T>() {

    override fun onInnerCodeError(code: Int, message: String) {
        super.onInnerCodeError(code, message)
        codeError(code, message)
    }

    override fun onError(e: Throwable) {
        super.onError(e)

        if (isNumeric(e.message)) {
            codeError(e.message!!.toInt(), e.cause?.message ?: "")
            return
        }

        try {
            if (e is ConnectException || e is TimeoutException || e is NetworkErrorException || e is UnknownHostException) {
                context.toast(R.string.netError)
            } else {
                context.toast(e.message ?: context.getString(R.string.unKnowError))
            }
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }

    private fun codeError(code: Int, message: String) {
        when(code) {
            401 -> context.toast(R.string.error_401)
            402 -> context.toast(R.string.error_402)
            403 -> context.toast(R.string.error_403)
            404 -> context.toast(R.string.error_404)
            422 -> context.toast(R.string.error_422)
            else -> context.toast("$code : $message")
        }
    }

    /**
     * 判断返回错误码是否为数字
     *
     * @param str String?
     * @return Boolean
     */
    private fun isNumeric(str: String?): Boolean {
        str?.let {
            var i = it.length
            while (--i >= 0) {
                if (!Character.isDigit(str[i])) {
                    return false
                }
            }
            return true
        }
        return false
    }

}