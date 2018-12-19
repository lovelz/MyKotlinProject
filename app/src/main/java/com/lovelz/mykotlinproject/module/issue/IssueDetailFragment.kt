package com.lovelz.mykotlinproject.module.issue

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.databinding.FragmentIssueDetailBinding
import com.lovelz.mykotlinproject.databinding.LayoutIssueHeaderBinding
import com.lovelz.mykotlinproject.di.ARouterInjector
import com.lovelz.mykotlinproject.model.ui.IssueUIModel
import com.lovelz.mykotlinproject.module.ARouterAddress
import com.lovelz.mykotlinproject.module.base.BaseListFragment
import com.lovelz.mykotlinproject.module.person.PersonActivity
import com.lovelz.mykotlinproject.ui.holder.IssueCommentHolder
import com.lovelz.mykotlinproject.ui.holder.base.DataBindingExpandUtils
import com.shuyu.commonrecycler.BindSuperAdapterManager
import kotlinx.android.synthetic.main.fragment_issue_detail.*

/**
 * @author lovelz
 * @date on 2018/12/17.
 */
@Route(path = ARouterAddress.IssueDetailFragment)
class IssueDetailFragment : BaseListFragment<FragmentIssueDetailBinding, IssueDetailViewModel>(), ARouterInjector {

    @Autowired
    @JvmField
    var userName = ""

    @Autowired
    @JvmField
    var reposName = ""

    @Autowired
    @JvmField
    var issueNumber = 0

    private lateinit var issueControlViewModel: IssueStatusController

    override fun getLayoutId(): Int {
        return R.layout.fragment_issue_detail
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        getViewModel().userName = userName
        getViewModel().reposName = reposName
        getViewModel().issueNumber = issueNumber
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        issueControlViewModel = IssueStatusController(context!!, adapter, issue_detail_bottom_container, this)

        getViewModel().liveIssueModel.observe(this, Observer {
            issueControlViewModel.initContainer(it)
        })
    }

    override fun getViewModelClass(): Class<IssueDetailViewModel> = IssueDetailViewModel::class.java

    override fun getRecyclerView(): RecyclerView? = baseRecycler

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun bindHolder(manager: BindSuperAdapterManager) {
        val binding: LayoutIssueHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_issue_header,
                null, false, DataBindingExpandUtils.LZDataBindingComponent())
        binding.issueUIModel = getViewModel().issueUIModel

        binding.issueHeaderImage.setOnClickListener {
            PersonActivity.gotoPersonInfo(getViewModel().issueUIModel.username)
        }

        manager.addHeaderView(binding.root)

        manager.bind(IssueUIModel::class.java, IssueCommentHolder.ID, IssueCommentHolder::class.java)
    }


}