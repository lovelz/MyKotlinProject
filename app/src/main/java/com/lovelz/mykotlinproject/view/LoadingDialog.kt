package com.lovelz.mykotlinproject.view

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import com.github.ybq.android.spinkit.style.Wave
import com.lovelz.mykotlinproject.R
import kotlinx.android.synthetic.main.layout_loading_dialog.*

/**
 * @author lovelz
 * @date on 2018/12/1.
 */
class LoadingDialog : Dialog {

    constructor(context: Context) : super(context)

    constructor(context: Context, theme: Int) : super(context, theme)

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        loadingBar.setIndeterminateDrawable(Wave())
        super.onWindowFocusChanged(hasFocus)
    }

    companion object {
        fun showDialog(context: Context, message: CharSequence?, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?): LoadingDialog? {
            val dialog = LoadingDialog(context, R.style.LoadingDialog)
            dialog.setContentView(R.layout.layout_loading_dialog)
            if (message.isNullOrBlank()) {
                dialog.loadingMessage?.visibility = View.GONE
            } else {
                dialog.loadingMessage?.text = message
            }
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(cancelable)
            dialog.setOnCancelListener(cancelListener)
            dialog.window?.attributes?.gravity = Gravity.CENTER
            val lp = dialog.window?.attributes
            lp?.dimAmount = 0.2f
            dialog.window?.attributes = lp
            dialog.show()
            return dialog
        }
    }

}