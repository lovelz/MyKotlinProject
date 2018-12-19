package com.lovelz.mykotlinproject.di.test.test2

import javax.inject.Inject
import javax.inject.Named

/**
 * @author lovelz
 * @date on 2018/12/7.
 */
class RoseLily @Inject constructor(@Named("Rose") val flower: Flower) {

    fun show(): String {
        return flower.whisper()
    }

}