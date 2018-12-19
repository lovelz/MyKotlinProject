package com.lovelz.mykotlinproject.di.test.test3

/**
 * @author lovelz
 * @date on 2018/12/7.
 */
class Pot constructor(val flower: Flower) {

    fun show(): String {
        return flower.whisper()
    }

}