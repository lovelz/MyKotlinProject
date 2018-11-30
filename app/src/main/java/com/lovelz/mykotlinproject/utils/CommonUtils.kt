package com.lovelz.mykotlinproject.utils

import android.graphics.Point
import android.widget.ImageView
import com.lovelz.mykotlinproject.R
import com.shuyu.github.kotlin.common.style.image.BlurTransformation
import com.shuyu.gsyimageloader.GSYImageLoaderManager
import com.shuyu.gsyimageloader.GSYLoadOption
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author lovelz
 * @date on 2018/11/26.
 */
object CommonUtils {

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

}