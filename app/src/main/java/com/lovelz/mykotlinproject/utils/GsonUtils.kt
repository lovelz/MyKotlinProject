package com.lovelz.mykotlinproject.utils

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.JsonParser

/**
 * @author lovelz
 * @date on 2018/11/26.
 */
object GsonUtils {

    /**
     * 将json字符串转换为bean对象
     */
    fun <T> parseJsonToBean(jsonString: String?, clazz: Class<T>): T {
        if (TextUtils.isEmpty(jsonString)) {
            throw RuntimeException("parseJsonToBean jsonString is empty!")
        }

        val jsonElement = JsonParser().parse(jsonString)
        if (jsonElement.isJsonNull) {
            throw RuntimeException("parseJsonToBean jsonElement is empty!")
        }

        if (!jsonElement.isJsonObject) {
            throw RuntimeException("parseJsonToBean jsonElement is not object!")
        }

        return Gson().fromJson(jsonElement, clazz)
    }

    fun jsonToString(obj: Any?): String {
        obj?.let {
            return Gson().toJson(obj)
        }
        throw IllegalArgumentException("obj can not be empty")
    }

    fun <T> parseJsonToArrayBeans(jsonString: String?, beanClazz: Class<T>): List<T> {
        if (TextUtils.isEmpty(jsonString)) {
            throw RuntimeException("parseJsonToArrayBeans jsonString is empty")
        }
        val jsonElement = JsonParser().parse(jsonString)
        if (jsonElement.isJsonNull) {
            throw RuntimeException("parseJsonToArrayBeans jsonElement is empty")
        }

        if (!jsonElement.isJsonArray) {
            throw RuntimeException("parseJsonToArrayBeans jsonElement is not array")
        }

        val jsonArray = jsonElement.asJsonArray
        val beans = ArrayList<T>()
        for (jsonElement2 in jsonArray) {
            val bean = Gson().fromJson(jsonElement2, beanClazz)
            beans.add(bean)
        }
        return beans
    }

}