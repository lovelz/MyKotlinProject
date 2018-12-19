package com.lovelz.mykotlinproject.module.main

import android.app.Application
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.module.dynamic.DynamicFragment
import com.lovelz.mykotlinproject.style.MyIconFont
import com.mikepenz.iconics.IconicsDrawable
import dagger.Module
import dagger.Provides
import devlight.io.library.ntb.NavigationTabBar

/**
 * @author lovelz
 * @date on 2018/12/3.
 */
@Module
class MainActivityModule {

    @Provides
    fun providerMainFragmentList(): List<Fragment> {
        return listOf(DynamicFragment(), DynamicFragment(), DynamicFragment())
    }

    @Provides
    fun providerMainTabModel(application: Application): List<NavigationTabBar.Model> {
        return listOf(
                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(MyIconFont.Icon.GSY_MAIN_DT)
                                .color(ContextCompat.getColor(application, R.color.subTextColor))
                                .sizeDp(20),
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.tabDynamic))
                        .build(),

                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(MyIconFont.Icon.GSY_MAIN_QS)
                                .color(ContextCompat.getColor(application, R.color.subTextColor))
                                .sizeDp(20),
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.tabRecommended))
                        .build(),

                NavigationTabBar.Model.Builder(
                        IconicsDrawable(application)
                                .icon(MyIconFont.Icon.GSY_MAIN_MY)
                                .color(ContextCompat.getColor(application, R.color.subTextColor))
                                .sizeDp(20),
                        Color.parseColor("#00000000"))
                        .title(application.getString(R.string.tabMy))
                        .build()
        )
    }

}