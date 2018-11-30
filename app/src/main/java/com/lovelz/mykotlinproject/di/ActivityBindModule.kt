package com.lovelz.mykotlinproject.di

import com.lovelz.mykotlinproject.di.annotation.ActivityScope
import com.lovelz.mykotlinproject.module.StartNavigationActivity
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

}