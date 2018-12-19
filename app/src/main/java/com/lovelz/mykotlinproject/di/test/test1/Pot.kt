package com.lovelz.mykotlinproject.di.test.test1

import javax.inject.Inject

/**
 * @author lovelz
 * @date on 2018/12/6.
 */
class Pot @Inject constructor(private val rose: Rose) {

    fun show(): String = rose.whisper()

}