package com.lovelz.mykotlinproject.di

import com.lovelz.mykotlinproject.module.dynamic.DynamicFragment
import com.lovelz.mykotlinproject.module.issue.IssueDetailFragment
import com.lovelz.mykotlinproject.module.login.LoginFragment
import com.lovelz.mykotlinproject.module.person.PersonFragment
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

@Module
abstract class MainFragmentBindModule {

    @ContributesAndroidInjector
    abstract fun contributeDynamicFragment(): DynamicFragment

}

@Module
abstract class PersonFragmentBindModule {

    @ContributesAndroidInjector
    abstract fun contributePersonFragment(): PersonFragment

}

@Module
abstract class IssueDetailFragmentBindModule {

    @ContributesAndroidInjector
    abstract fun contributeIssueDetailFragment(): IssueDetailFragment

}