package com.lovelz.mykotlinproject.utils

import android.content.Context
import android.graphics.Point
import android.widget.ImageView
import androidx.core.net.toUri
import com.lovelz.mykotlinproject.App
import com.lovelz.mykotlinproject.AppConfig
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.module.person.PersonActivity
import com.shuyu.github.kotlin.common.style.image.BlurTransformation
import com.shuyu.gsyimageloader.GSYImageLoaderManager
import com.shuyu.gsyimageloader.GSYLoadOption
import org.jetbrains.anko.browse
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author lovelz
 * @date on 2018/11/26.
 */
object CommonUtils {

    private const val MILLIS_LIMIT = 1000.0

    private const val SECONDS_LIMIT = 60 * MILLIS_LIMIT

    private const val MINUTES_LIMIT = 60 * SECONDS_LIMIT

    private const val HOURS_LIMIT = 24 * MINUTES_LIMIT

    private const val DAYS_LIMIT = 30 * HOURS_LIMIT

    /**
     * 加载用户头像
     * @param imageView ImageView
     * @param url String
     * @param size Point
     */
    fun loadUserHeaderImage(imageView: ImageView, url: String, size: Point = Point(50.dp, 50.dp)) {
        val option = GSYLoadOption()
                .setDefaultImg(R.mipmap.ic_launcher)
                .setErrorImg(R.mipmap.ic_launcher)
                .setCircle(true)
                .setSize(size)
                .setUri(url)

        GSYImageLoaderManager.sInstance.imageLoader().loadImage(option, imageView, null)
    }

    /**
     * 加载高斯模糊图片
     * @param imageView ImageView
     * @param url String
     */
    fun loadImageBlur(imageView: ImageView, url: String) {
        val process = BlurTransformation()
        val option = GSYLoadOption()
                .setDefaultImg(R.mipmap.ic_launcher)
                .setErrorImg(R.mipmap.ic_launcher)
                .setUri(url)
                .setTransformations(process)

        GSYImageLoaderManager.sInstance.imageLoader().loadImage(option, imageView, null)
    }

    fun getDateStr(date: Date?): String {
        date?.toString()?.let {
            if (it.length < 10) {
                return it
            }
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date).substring(0, 10)
        }
        return ""
    }

    fun getNewsTimeStr(date: Date?): String {
        if (date == null) {
            return ""
        }
        val subTime = Date().time - date.time
        return when {
            subTime < MILLIS_LIMIT -> App.instance.getString(R.string.justNow)
            subTime < SECONDS_LIMIT -> "${Math.round(subTime / MILLIS_LIMIT)}  ${App.instance.getString(R.string.secondAgo)}"
            subTime < MINUTES_LIMIT -> "${Math.round(subTime / SECONDS_LIMIT)}  ${App.instance.getString(R.string.minutesAgo)}"
            subTime < HOURS_LIMIT -> "${Math.round(subTime / MINUTES_LIMIT)}  ${App.instance.getString(R.string.hoursAgo)}"
            subTime < DAYS_LIMIT -> "${Math.round(subTime / HOURS_LIMIT)}  ${App.instance.getString(R.string.daysAgo)}"
            else -> getDateStr(date)
        }
    }

    fun getUserHtmlUrl(userName: String): String = AppConfig.GITHUB_BASE_URL + userName

    fun launchUrl(context: Context, url: String?) {
        if (url == null || url.isEmpty()) return
        val parseUrl = url.toUri()
        var isImage = isImageEnd(parseUrl.toString())
        if (parseUrl.toString().endsWith("?raw=true")) {
            isImage = isImageEnd(parseUrl.toString().replace("?raw=true", ""))
        }

        if (isImage) {
            var imageUrl = url
            if (!parseUrl.toString().endsWith("?raw=true")) {
                imageUrl = "$url?raw=true"
            }
            context.toast("这是图片")
            return
        }

        if (parseUrl.host == "github.com" && parseUrl.path.isNotEmpty()) {
            val pathNames = parseUrl.path.split("/")
            if (pathNames.size == 2) {
                val userName = pathNames[1]
                PersonActivity.gotoPersonInfo(userName)
            } else if (pathNames.size >= 3) {

            }
        } else if (url.startsWith("http")) {
            context.browse(url)
        }
    }

    private val sImageEndTag = arrayListOf(".png", ".jpg", ".jpeg", ".gif", ".svg")

    /**
     * 是否为图片格式
     */
    fun isImageEnd(path: String): Boolean {
        var isImage = false
        sImageEndTag.forEach {
            if (path.indexOf(it) + it.length == path.length) {
                isImage = true
            }
        }
        return isImage
    }

}