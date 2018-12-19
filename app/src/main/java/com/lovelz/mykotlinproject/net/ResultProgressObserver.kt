package com.lovelz.mykotlinproject.net

import android.content.Context
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.view.LoadingDialog

/**
 * @author lovelz
 * @date on 2018/12/1.
 */
abstract class ResultProgressObserver<T>(private val context: Context, private val needLoading: Boolean = true) : ResultTipObserver<T>(context) {

    private var loadingDialog: LoadingDialog? = null

    private var loadingText: String? = null

    constructor(context: Context, loadingText: String?) : this(context) {
        this.loadingText = loadingText
    }

    override fun onRequestStart() {
        super.onRequestStart()
        if (needLoading) {
            showLoading()
        }
    }

    override fun onRequestEnd() {
        super.onRequestEnd()
        dismissLoading()
    }

    private fun getLoadingText(): String {
        return if (loadingText.isNullOrBlank()) context.getString(R.string.loading) else loadingText!!
    }

    private fun showLoading() {
        dismissLoading()
        loadingDialog = LoadingDialog.showDialog(context, getLoadingText(), false, null)
    }

    private fun dismissLoading() {
        loadingDialog?.apply {
            if (this.isShowing) {
                this.dismiss()
            }
        }
    }

}