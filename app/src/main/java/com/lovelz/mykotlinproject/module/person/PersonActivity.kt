package com.lovelz.mykotlinproject.module.person

import android.os.Bundle
import android.support.v4.view.LayoutInflaterCompat
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lovelz.mykotlinproject.di.ARouterInjector
import com.lovelz.mykotlinproject.module.ARouterAddress
import com.lovelz.mykotlinproject.module.base.BaseFragment
import com.lovelz.mykotlinproject.module.base.BaseFragmentActivity
import com.mikepenz.iconics.context.IconicsLayoutInflater2

/**
 * @author lovelz
 * @date on 2018/12/8.
 */
@Route(path = ARouterAddress.PersonActivity)
class PersonActivity : BaseFragmentActivity(), ARouterInjector {

    @Autowired
    @JvmField
    var userName = ""

    companion object {
        fun gotoPersonInfo(userName: String) {
            getRouterNavigation(ARouterAddress.PersonActivity, userName).navigation()
        }

        fun getRouterNavigation(uri: String, userName: String): Postcard {
            return ARouter.getInstance()
                    .build(uri)
                    .withString("userName", userName)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(layoutInflater, IconicsLayoutInflater2(delegate))
        super.onCreate(savedInstanceState)
    }

    override fun getInitFragment(): BaseFragment<*> {
        return getRouterNavigation(ARouterAddress.PersonFragment, userName).navigation() as PersonFragment
    }

    override fun getToolBarTitle(): String = userName

}