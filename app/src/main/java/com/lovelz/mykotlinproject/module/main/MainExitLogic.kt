package com.lovelz.mykotlinproject.module.main

import com.lovelz.mykotlinproject.R
import org.jetbrains.anko.toast

/**
 * @author lovelz
 * @date on 2018/12/7.
 */
class MainExitLogic(private val activity: MainActivity) {

    var firstTime = 0L

    fun backPress() {
        val secondTime = System.currentTimeMillis()
        if (secondTime - firstTime >= 2000) {
            activity.toast(R.string.doublePressExit)
            firstTime = secondTime
            return
        } else {
            System.exit(0)
        }
    }

}