package com.lovelz.mykotlinproject.ui.base

import android.databinding.BindingAdapter
import android.databinding.DataBindingComponent
import android.widget.ImageView
import com.lovelz.mykotlinproject.utils.CommonUtils

/**
 * DataBinding拓展适配
 *
 * @author lovelz
 * @date on 2018/11/27.
 */
class DataBindingExpandUtils {

    companion object {

        /**
         * 高斯模糊图片加载
         * @param view ImageView
         * @param url String?
         */
        @BindingAdapter("image_blur")
        fun loadImageBlur(view: ImageView, url: String?) {
            CommonUtils.loadImageBlur(view, url ?: "")
        }

    }

    class LZDataBindingComponent : DataBindingComponent {
        override fun getCompanion(): DataBindingExpandUtils.Companion = DataBindingExpandUtils
    }

}