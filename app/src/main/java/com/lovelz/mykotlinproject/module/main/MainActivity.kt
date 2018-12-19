package com.lovelz.mykotlinproject.module.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.model.AppGlobalModel
import com.lovelz.mykotlinproject.module.dynamic.DynamicFragment
import com.lovelz.mykotlinproject.repository.LoginRepository
import com.lovelz.mykotlinproject.ui.adapter.FragmentPagerViewAdapter
import com.lovelz.mykotlinproject.view.DoubleNavigationTabBar
import com.mikepenz.iconics.context.IconicsLayoutInflater2
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, Toolbar.OnMenuItemClickListener {

    @Inject
    lateinit var globalModel: AppGlobalModel

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    /**
     * fragment列表
     */
    @Inject
    lateinit var mainFragmentList: MutableList<Fragment>

    /**
     * tab列表
     */
    @Inject
    lateinit var mainTabModel: MutableList<NavigationTabBar.Model>

    @Inject
    lateinit var loginRepository: LoginRepository

    private val exitLogic = MainExitLogic(this)

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(layoutInflater, IconicsLayoutInflater2(delegate))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPager()

        initToolbar()

        MainDrawerController(this, home_tool_bar, loginRepository, globalModel)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_search -> toast("搜索啦")
        }
        return true
    }

    private fun initViewPager() {
        home_view_pager.adapter = FragmentPagerViewAdapter(mainFragmentList, supportFragmentManager)
        home_navigation_tab_bar.models = mainTabModel
        home_navigation_tab_bar.setViewPager(home_view_pager, 0)
        home_view_pager.offscreenPageLimit = mainFragmentList.size

        //首页双击刷新
        home_navigation_tab_bar.tabDoubleClickListener = object : DoubleNavigationTabBar.TabDoubleClickListener {
            override fun onDoubleClick(position: Int) {
                if (position == 0) {
                    val fragment = mainFragmentList[position] as DynamicFragment
                    fragment.showRefresh()
                }
            }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(home_tool_bar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
        }
        home_tool_bar.setTitle(R.string.app_name)
        home_tool_bar.setOnMenuItemClickListener(this)
    }

    override fun onBackPressed() {
        exitLogic.backPress()
    }

}
