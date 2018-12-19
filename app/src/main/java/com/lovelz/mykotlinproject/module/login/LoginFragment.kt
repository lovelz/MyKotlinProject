package com.lovelz.mykotlinproject.module.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.databinding.FragmentLoginBinding
import com.lovelz.mykotlinproject.module.base.BaseFragment
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * 登录
 *
 * @author lovelz
 * @date on 2018/11/28.
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        exitFull()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(mainView: View?) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LoginViewModel::class.java)
        binding?.loginViewModel = loginViewModel

        loginViewModel.loginResult.observe(this, Observer { result ->
            if (result != null && result == true) {
                navigationPopUpTo(view, null, R.id.action_nav_login_to_main, true)
            } else {
                activity?.toast(R.string.LoginFailTip)
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }
}