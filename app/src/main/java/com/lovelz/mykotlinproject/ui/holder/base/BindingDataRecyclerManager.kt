package com.lovelz.mykotlinproject.ui.holder.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shuyu.commonrecycler.BindRecyclerBaseHolder
import com.shuyu.commonrecycler.BindSuperAdapterManager
import java.lang.reflect.Constructor

/**
 * 增加对DataBinding的支持
 * @author lovelz
 * @date on 2018/12/3.
 */
class BindingDataRecyclerManager : BindSuperAdapterManager() {

    /**
     * 根据参数构造
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T> contructorHolder(context: Context, parent: ViewGroup, classType: Class<out BindRecyclerBaseHolder>?, layoutId: Int): T? {
        var `object`: Constructor<*>? = null
        var constructorFirst = true

        val itemTextBinding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false, DataBindingExpandUtils.LZDataBindingComponent())

        try {
            `object` = classType?.getDeclaredConstructor(Context::class.java, View::class.java, ViewDataBinding::class.java)
        } catch (e: NoSuchMethodException) {
            constructorFirst = false
            //e.printStackTrace();
        }

        if (!constructorFirst) {
            try {
                `object` = classType?.getDeclaredConstructor(View::class.java)
            } catch (e: NoSuchMethodException) {
                //e.printStackTrace();
            }

        }

        if (`object` == null) {
            throw RuntimeException("Holder Constructor Error For : " + classType?.name)
        }

        try {
            `object`.isAccessible = true
            return if (constructorFirst) {
                `object`.newInstance(context, itemTextBinding.root, itemTextBinding) as T
            } else {
                `object`.newInstance(itemTextBinding.root) as T
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

}