package com.lovelz.mykotlinproject.module.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.lovelz.mykotlinproject.di.Injectable
import com.lovelz.mykotlinproject.ui.base.DataBindingExpandUtils

/**
 * @author lovelz
 * @date on 2018/11/27.
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment(), Injectable {

    var binding by autoCleared<T>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                getLayoutId(),
                container,
                false,
                DataBindingExpandUtils.LZDataBindingComponent())
        onCreateView(binding?.root)
        return binding?.root
    }

    abstract fun onCreateView(mainView: View?)

    abstract fun getLayoutId(): Int

    /**
     * Navigation的页面跳转
     */
    fun navigationPopUpTo(view: View, args: Bundle?, actionId: Int, finishStack: Boolean) {
        val controller = Navigation.findNavController(view)
        controller.navigate(actionId, args, NavOptions.Builder().setPopUpTo(controller.graph.id, true).build())
        if (finishStack) {
            activity?.finish()
        }
    }

    fun enterFull() {
        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    }

    fun exitFull() {
        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    }

}