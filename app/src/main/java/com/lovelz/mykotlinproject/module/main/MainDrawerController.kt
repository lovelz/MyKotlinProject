package com.lovelz.mykotlinproject.module.main

import android.app.Activity
import android.support.v7.widget.Toolbar
import androidx.core.net.toUri
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.model.AppGlobalModel
import com.lovelz.mykotlinproject.repository.LoginRepository
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import org.jetbrains.anko.toast

/**
 * 主页Drawer控制器
 *
 * @author lovelz
 * @date on 2018/12/7.
 */
class MainDrawerController(private val activity: Activity, toolbar: Toolbar, loginRepository: LoginRepository,
                           globalModel: AppGlobalModel) {

    var drawer: Drawer? = null

    init {
        drawer = DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withSelectedItem(-1)
                .addDrawerItems(
                        PrimaryDrawerItem().withName(R.string.feedback)
                                .withTextColorRes(R.color.colorPrimary)
                                .withOnDrawerItemClickListener{ view, position, drawerItem ->
                                    activity.toast(R.string.feedback)
                                    unSelect(drawerItem)
                                    true
                                }
                )
                .addDrawerItems(
                        PrimaryDrawerItem().withName(R.string.person)
                                .withTextColorRes(R.color.colorPrimary)
                                .withOnDrawerItemClickListener{ view, position, drawerItem ->
                                    activity.toast(R.string.person)
                                    unSelect(drawerItem)
                                    true
                                }
                )
                .addDrawerItems(
                        PrimaryDrawerItem().withName(R.string.update)
                                .withTextColorRes(R.color.colorPrimary)
                                .withOnDrawerItemClickListener{ view, position, drawerItem ->
                                    activity.toast(R.string.update)
                                    unSelect(drawerItem)
                                    true
                                }
                )
                .addDrawerItems(
                        PrimaryDrawerItem().withName(R.string.about)
                                .withTextColorRes(R.color.colorPrimary)
                                .withOnDrawerItemClickListener{ view, position, drawerItem ->
                                    activity.toast(R.string.about)
                                    unSelect(drawerItem)
                                    true
                                }
                )
                .addDrawerItems(
                        PrimaryDrawerItem().withName(R.string.LoginOut)
                                .withTextColorRes(R.color.red)
                                .withOnDrawerItemClickListener{ view, position, drawerItem ->
                                    loginRepository.logout(view.context)
                                    unSelect(drawerItem)
                                    true
                                }
                )
                .withAccountHeader(AccountHeaderBuilder().withActivity(activity)
                        .addProfiles(ProfileDrawerItem().withName(globalModel.userObservable.login)
                                .withSetSelected(false)
                                .withIcon(globalModel.userObservable.avatarUrl?.toUri())
                                .withEmail(globalModel.userObservable.email ?: ""))
                        .withHeaderBackground(R.color.colorPrimary)
                        .withSelectionListEnabled(false)
                        .build())
                .build()
    }

    private fun unSelect(drawerItem: IDrawerItem<*, *>) {
        drawerItem.withSetSelected(false)
        drawer?.adapter?.notifyAdapterDataSetChanged()
    }

}