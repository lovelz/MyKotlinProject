package com.lovelz.mykotlinproject.di

import com.lovelz.mykotlinproject.di.annotation.ActivityScope
import com.lovelz.mykotlinproject.module.StartNavigationActivity
import com.lovelz.mykotlinproject.module.issue.IssueDetailActivity
import com.lovelz.mykotlinproject.module.main.MainActivity
import com.lovelz.mykotlinproject.module.main.MainActivityModule
import com.lovelz.mykotlinproject.module.person.PersonActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author lovelz
 * @date on 2018/11/24.
 */
@Module
abstract class ActivityBindModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [StartFragmentBindModule::class])
    abstract fun StartNavigationActivityInjector(): StartNavigationActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class, MainFragmentBindModule::class])
    abstract fun MainActivityInjector(): MainActivity


    @ActivityScope
    @ContributesAndroidInjector(modules = [PersonFragmentBindModule::class])
    abstract fun PersonActivityInjector(): PersonActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [IssueDetailFragmentBindModule::class])
    abstract fun IssueDetailActivityInjector(): IssueDetailActivity

}