package com.lovelz.mykotlinproject.utils

import android.content.Context
import android.content.SharedPreferences
import com.lovelz.mykotlinproject.App
import kotlin.reflect.KProperty

/**
 * @author lovelz
 * @date on 2018/11/24.
 */
class LZPreference<T>(private val keyName: String, private val default: T) {

    private val prefs: SharedPreferences by lazy {App.instance.getSharedPreferences(keyName, Context.MODE_PRIVATE)}

    operator fun getValue(thisRes: Any?, property: KProperty<*>): T {
        return getSharePreference(keyName, default)
    }

    operator fun setValue(thisRes: Any?, property: KProperty<*>, value: T) {
        saveSharePreference(keyName, value)
    }

    private fun saveSharePreference(keyName: String, value: T) = with(prefs.edit()) {
        when(value) {
            is Int -> putInt(keyName, value)
            is String -> putString(keyName, value)
            is Long -> putLong(keyName, value)
            is Float -> putFloat(keyName, value)
            is Boolean -> putBoolean(keyName, value)
            else -> throw IllegalArgumentException("Type is Error, can not be put!")
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getSharePreference(keyName: String, default: T): T = with(prefs) {
        val res: Any = when(default) {
            is Int -> getInt(keyName, default)
            is String -> getString(keyName, default)
            is Long -> getLong(keyName, default)
            is Float -> getFloat(keyName, default)
            is Boolean -> getBoolean(keyName, default)
            else -> throw IllegalArgumentException("Type is Error, can not be get!")
        }
        return res as T
    }


}