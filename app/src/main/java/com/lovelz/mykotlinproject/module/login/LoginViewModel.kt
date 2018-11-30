package com.lovelz.mykotlinproject.module.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ObservableField
import android.view.View
import com.lovelz.mykotlinproject.AppConfig
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.repository.LoginRepository
import com.lovelz.mykotlinproject.utils.LZPreference
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * @author lovelz
 * @date on 2018/11/29.
 */
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {

    private val usernameStorage: String by LZPreference(AppConfig.USER_NAME, "")

    private val passwordStorage: String by LZPreference(AppConfig.USER_PASSWORD, "")

    /**
     * 用户名
     */
    val username = ObservableField<String>()

    /**
     * 密码
     */
    val password = ObservableField<String>()

    /**
     * 登录的结果
     */
    val loginResult = MutableLiveData<Boolean>()

    init {
        //使用本地存储的用户名和密码
        username.set(usernameStorage)
        password.set(passwordStorage)
    }

    /**
     * 点击事件绑定
     * @param view View
     */
    fun onSubmitClick(view: View) {
        val username = this.username.get()
        val password = this.password.get()

        username?.apply {
            if (this.isEmpty()) {
                view.context.toast(R.string.LoginNameTip)
                return
            }
        }

        password?.apply {
            if (this.isEmpty()) {
                view.context.toast(R.string.LoginPWTip)
                return
            }
        }

        login(view.context)
    }

    /**
     * 登录
     * @param context Context
     */
    fun login(context: Context) {
        loginRepository.login(context, username.get()!!.trim(), password.get()!!.trim(), loginResult)
    }

}