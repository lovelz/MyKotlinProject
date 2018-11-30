package com.lovelz.mykotlinproject.module.welcome

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.lovelz.mykotlinproject.AppConfig
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.databinding.FragmentWelcomeBinding
import com.lovelz.mykotlinproject.model.AppGlobalModel
import com.lovelz.mykotlinproject.model.bean.User
import com.lovelz.mykotlinproject.model.conversion.UserConversion
import com.lovelz.mykotlinproject.module.base.BaseFragment
import com.lovelz.mykotlinproject.utils.GsonUtils
import com.lovelz.mykotlinproject.utils.LZPreference
import kotlinx.android.synthetic.main.fragment_welcome.*
import javax.inject.Inject

/**
 * @author lovelz
 * @date on 2018/11/27.
 */
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    //by: 委托 后者方法交给前面
    private var userInfoStorage: String by LZPreference(AppConfig.USER_INFO, "")

    private var accessTokenStorage by LZPreference(AppConfig.ACCESS_TOKEN, "")

    @Inject
    lateinit var appGlobalModel: AppGlobalModel

    override fun onCreateView(mainView: View?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_welcome
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //设置动画速度
        welcome_animation.speed = 5.0f

        //延迟两秒进入下个页面
        Handler().postDelayed({
            goNext(view)
        }, 2000)
    }

    private fun goNext(view: View) {
        if (accessTokenStorage.isEmpty()) {
            enterLogin(view)
        } else {
            if (userInfoStorage.isEmpty()) {
                enterLogin(view)
            } else {
                val user = GsonUtils.parseJsonToBean(userInfoStorage, User::class.java)
                UserConversion.cloneDataFromUser(context, user, appGlobalModel.userObservable)
                //进入主页
                navigationPopUpTo(view, null, R.id.action_nav_wel_to_main, true)
            }
        }
    }

    /**
     * 进入登录页面
     * @param view View
     */
    private fun enterLogin(view: View) {
        navigationPopUpTo(view, null, R.id.action_nav_wel_to_login, false)
    }

}