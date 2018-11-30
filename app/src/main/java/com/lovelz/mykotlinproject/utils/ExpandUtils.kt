package com.lovelz.mykotlinproject.utils

import android.content.res.Resources
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP

/**
 * @author lovelz
 * @date on 2018/11/26.
 */

private val metrics = Resources.getSystem().displayMetrics

val Float.dp: Float
    get() = TypedValue.applyDimension(COMPLEX_UNIT_DIP, this, metrics)

val Int.dp: Int
    get() = TypedValue.applyDimension(COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()