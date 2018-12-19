package com.lovelz.mykotlinproject.ui.holder.base

import android.databinding.BindingAdapter
import android.databinding.DataBindingComponent
import android.graphics.Point
import android.widget.ImageView
import android.widget.TextView
import com.lovelz.mykotlinproject.style.MarkDownConfig
import com.lovelz.mykotlinproject.utils.CommonUtils
import com.lovelz.mykotlinproject.utils.dp
import com.lovelz.mykotlinproject.view.WebViewContainer
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.view.IconicsImageView
import ru.noties.markwon.Markwon

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

        @BindingAdapter("userHeaderUrl", "userHeaderSize", requireAll = false)
        fun loadImage(view: ImageView, url: String?, size: Int = 50) {
            CommonUtils.loadUserHeaderImage(view, url ?: "", Point(size.dp, size.dp))
        }

        @BindingAdapter("webViewUrl")
        fun webViewUrl(view: WebViewContainer?, url: String?) {
            view?.apply {
                webView.isVerticalScrollBarEnabled = false
                webView.loadUrl(url)
            }
        }

        /**
         * IconicsImageView图标加载
         */
        @BindingAdapter("iiv_icon", "iiv_color", requireAll = false)
        fun editTextKeyListener(view: IconicsImageView?, value: String?, colorId: Int?) {
            if (view == null || value == null) {
                return
            }
            val drawable = IconicsDrawable(view.context).icon(value)
            colorId?.apply {
                drawable.color(colorId)
            }
            view.icon = drawable
        }

        @BindingAdapter("markdownText", "style", requireAll = false)
        fun markdownText(view: TextView?, text: String?, style: String? = "default") {
            view?.apply {
                Markwon.setMarkdown(this, MarkDownConfig.getConfig(view.context), text ?: "")
            }
        }

    }

    class LZDataBindingComponent : DataBindingComponent {
        override fun getCompanion(): DataBindingExpandUtils.Companion = DataBindingExpandUtils
    }

}