package com.lovelz.mykotlinproject.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lovelz.mykotlinproject.LZViewModelFactory
import com.lovelz.mykotlinproject.di.annotation.ViewModelKey
import com.lovelz.mykotlinproject.module.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author lovelz
 * @date on 2018/11/26.
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: LZViewModelFactory): ViewModelProvider.Factory

}