package com.lovelz.mykotlinproject.module.person

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.di.ARouterInjector
import com.lovelz.mykotlinproject.module.ARouterAddress
import com.lovelz.mykotlinproject.module.base.BaseUserInfoFragment
import com.lovelz.mykotlinproject.utils.CommonUtils
import com.lovelz.mykotlinproject.utils.copy
import org.jetbrains.anko.browse
import org.jetbrains.anko.share
import org.jetbrains.anko.toast

/**
 * @author lovelz
 * @date on 2018/12/8.
 */
@Route(path = ARouterAddress.PersonFragment)
class PersonFragment : BaseUserInfoFragment<PersonViewModel>(), ARouterInjector {

    @Autowired
    @JvmField
    var userName = ""

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        binding?.userInfoViewModel = getViewModel()

    }

    override fun getLoginName(): String? = userName

    override fun getViewModelClass(): Class<PersonViewModel> = PersonViewModel::class.java

    override fun actionOpenByBrowser() {
        context?.browse(CommonUtils.getUserHtmlUrl(userName))
    }

    override fun actionCopy() {
        context?.copy(CommonUtils.getUserHtmlUrl(userName))
        context?.toast(R.string.hadCopy)
    }

    override fun actionShare() {
        context?.share(CommonUtils.getUserHtmlUrl(userName))
    }

}