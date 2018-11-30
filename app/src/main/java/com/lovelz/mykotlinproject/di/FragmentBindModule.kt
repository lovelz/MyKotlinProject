package com.lovelz.mykotlinproject.di

import com.lovelz.mykotlinproject.module.login.LoginFragment
import com.lovelz.mykotlinproject.module.welcome.WelcomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author lovelz
 * @date on 2018/11/28.
 */

@Module
abstract class StartFragmentBindModule {

    @ContributesAndroidInjector
    abstract fun contributeWelcomeFragment(): WelcomeFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

}