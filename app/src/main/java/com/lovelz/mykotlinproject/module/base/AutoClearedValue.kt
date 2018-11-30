package com.lovelz.mykotlinproject.module.base


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author lovelz
 * @date on 2018/11/27.
 */
class AutoClearedValue<T : Any?>(val fragment: Fragment) : ReadWriteProperty<Fragment, T?> {

    private var _value: T? = null

    init {
        fragment.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                _value = null
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
        return _value
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T?) {
        _value = value
    }

}

fun <T : Any?> Fragment.autoCleared() = AutoClearedValue<T?>(this)