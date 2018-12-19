package com.lovelz.mykotlinproject.module.issue

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lovelz.mykotlinproject.di.ARouterInjector
import com.lovelz.mykotlinproject.module.ARouterAddress
import com.lovelz.mykotlinproject.module.base.BaseFragment
import com.lovelz.mykotlinproject.module.base.BaseFragmentActivity

/**
 * 问题详情
 *
 * @author lovelz
 * @date on 2018/12/17.
 */
@Route(path = ARouterAddress.IssueDetailActivity)
class IssueDetailActivity : BaseFragmentActivity(), ARouterInjector {

    @Autowired
    @JvmField
    var userName = ""

    @Autowired
    @JvmField
    var reposName = ""

    @Autowired
    @JvmField
    var issueNumber = 0

    companion object {

        fun gotoIssueDetail(userName: String, reposName: String, issueNumber: Int) {
            getRouterNavigation(ARouterAddress.IssueDetailActivity, userName, reposName, issueNumber).navigation()
        }

        fun getRouterNavigation(uri: String, userName: String, reposName: String, issueNumber: Int): Postcard {
            return ARouter.getInstance()
                    .build(uri)
                    .withString("userName", userName)
                    .withString("reposName", reposName)
                    .withInt("issueNumber", issueNumber)
        }

    }

    override fun getInitFragment(): BaseFragment<*> {
        return getRouterNavigation(ARouterAddress.IssueDetailFragment, userName, reposName, issueNumber).navigation() as IssueDetailFragment
    }

    override fun getToolBarTitle(): String {
        return "$userName/$reposName"
    }

}