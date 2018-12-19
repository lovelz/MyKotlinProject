package com.lovelz.mykotlinproject.module.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import com.lovelz.mykotlinproject.R

/**
 * @author lovelz
 * @date on 2018/12/8.
 */
abstract class BaseActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener, PopupMenu.OnMenuItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initTitle()
    }

    /**
     * 初始化title
     */
    private fun initTitle() {
        setSupportActionBar(getToolBar())
        val actionBar = supportActionBar
        actionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }
        getToolBar().title = getToolBarTitle()
        getToolBar().setOnMenuItemClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.default_toolbar_menu, menu)
        return true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_more -> {
                val pop = PopupMenu(this, getToolBar())
                pop.menuInflater.inflate(R.menu.default_toolbar_pop_menu, pop.menu)
                pop.gravity = Gravity.END
                pop.show()
                pop.setOnMenuItemClickListener(this)
            }
            R.id.action_browser -> {
                actionOpenByBrowser()
            }
            R.id.action_copy -> {
                actionCopy()
            }
            R.id.action_share -> {
                actionShare()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    open fun actionOpenByBrowser() {}

    open fun actionCopy() {}

    open fun actionShare() {}

    abstract fun getToolBarTitle(): String

    abstract fun getToolBar(): Toolbar

    abstract fun getLayoutId(): Int

}