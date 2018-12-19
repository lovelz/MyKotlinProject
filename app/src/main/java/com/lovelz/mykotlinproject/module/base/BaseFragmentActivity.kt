package com.lovelz.mykotlinproject.module.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.lovelz.mykotlinproject.R
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_fragment_container.*
import javax.inject.Inject

/**
 * @author lovelz
 * @date on 2018/12/8.
 */
abstract class BaseFragmentActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private var fragment: BaseFragment<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragment = getInitFragment()
        addFragment(fragment!!, R.id.activity_fragment_container_id)
    }

    override fun getLayoutId(): Int = R.layout.activity_fragment_container

    override fun actionOpenByBrowser() {
        fragment?.actionOpenByBrowser()
    }

    override fun actionCopy() {
        fragment?.actionCopy()
    }

    override fun actionShare() {
        fragment?.actionShare()
    }

    abstract fun getInitFragment(): BaseFragment<*>

    override fun getToolBar(): Toolbar = activity_fragment_container_toolbar

    override fun supportFragmentInjector() = dispatchingAndroidInjector

}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

