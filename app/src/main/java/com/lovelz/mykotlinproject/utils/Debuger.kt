package com.lovelz.mykotlinproject.utils

import android.text.TextUtils
import android.util.Log
import com.lovelz.mykotlinproject.BuildConfig

/**
 * @author lovelz
 * @date on 2018/11/24.
 */
object Debuger {

    private val DEBUGER_TAG = "LZDebugerLog"

    private var debugMode = BuildConfig.DEBUG

    fun printLog(tag: String, log: String?) {
        if (debugMode && log != null) {
            if (!TextUtils.isEmpty(log)) {
                Log.i(tag, log)
            }
        }
    }

    fun printLog(log: String?) {
        printLog(DEBUGER_TAG, log)
    }

}