package com.lovelz.mykotlinproject.net

import android.net.Uri
import com.lovelz.mykotlinproject.utils.GsonUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author lovelz
 * @date on 2018/11/26.
 */
class PageInfoInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val linkString: String? = response.headers()["Link"]

        val pageInfo = PageInfo()

        linkString?.apply {
            val links = this.split(",")
            links.forEach {
                when {
                    it.contains("prev") -> pageInfo.prev = parseNumber(it)
                    it.contains("next") -> pageInfo.next = parseNumber(it)
                    it.contains("first") -> pageInfo.first = parseNumber(it)
                    it.contains("last") -> pageInfo.last = parseNumber(it)
                }
            }
        }

        return response.newBuilder().addHeader("page_info", GsonUtils.jsonToString(pageInfo)).build()
    }

    private fun parseNumber(item: String?): Int {
        item?.let {
            val startFlag = "<"
            val endFlag = ">"
            val startIndex = item.indexOf(startFlag)
            val endIndex = item.indexOf(endFlag)
            if (startIndex <= 0 || endIndex <= 0) {
                return -1
            }

            val startStart = startIndex + startFlag.length
            val url = item.substring(startStart, endIndex)
            return Uri.parse(url).getQueryParameter("page").toInt()
        }
        return -1
    }

    data class PageInfo(
            var prev: Int = -1,
            var next: Int = -1,
            var first: Int = -1,
            var last: Int = -1
    )

}