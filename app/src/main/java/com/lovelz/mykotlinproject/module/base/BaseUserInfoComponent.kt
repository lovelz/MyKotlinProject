package com.lovelz.mykotlinproject.module.base

import android.app.Application
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView
import android.view.View
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.databinding.FragmentUserInfoBinding
import com.lovelz.mykotlinproject.databinding.LayoutUserHeaderBinding
import com.lovelz.mykotlinproject.model.ui.EventUIModel
import com.lovelz.mykotlinproject.model.ui.UserUIModel
import com.lovelz.mykotlinproject.module.person.PersonActivity
import com.lovelz.mykotlinproject.repository.UserRepository
import com.lovelz.mykotlinproject.ui.holder.EventHolder
import com.lovelz.mykotlinproject.ui.holder.UserHolder
import com.lovelz.mykotlinproject.ui.holder.base.DataBindingExpandUtils
import com.lovelz.mykotlinproject.utils.EventUtils
import com.shuyu.commonrecycler.BindSuperAdapterManager
import kotlinx.android.synthetic.main.fragment_user_info.*
import org.jetbrains.anko.toast

/**
 * @author lovelz
 * @date on 2018/12/8.
 */
abstract class BaseUserInfoFragment<T : BaseUserInfoViewModel> : BaseListFragment<FragmentUserInfoBinding, T>() {

    override fun getLayoutId(): Int = R.layout.fragment_user_info

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        getViewModel().login = getLoginName()
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
        val item = adapter?.dataList?.get(position)
        when(item) {
            is EventUIModel -> {
                EventUtils.eventAction(activity, adapter?.dataList?.get(position) as EventUIModel)
            }
            is UserUIModel -> {
                PersonActivity.gotoPersonInfo(item.login!!)
            }
        }
    }

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun getRecyclerView(): RecyclerView? = fragment_my_recycler

    override fun bindHolder(manager: BindSuperAdapterManager) {
        val binding: LayoutUserHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_user_header,
                null, false, DataBindingExpandUtils.LZDataBindingComponent())
        binding.userUIModel = getViewModel().getUserModel()
        binding.baseUserViewModel = getViewModel()
        binding.userHeaderNotify.visibility = View.GONE
        manager.addHeaderView(binding.root)
        bindHeader(binding)

        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
        manager.bind(UserUIModel::class.java, UserHolder.ID, UserHolder::class.java)
    }

    private fun bindHeader(binding: LayoutUserHeaderBinding) {

    }

    abstract fun getLoginName(): String?

}

abstract class BaseUserInfoViewModel constructor(private val userRepository: UserRepository, private val application: Application) : BaseViewModel(application) {

    val focusIcon = ObservableField<String>()

    var login: String? = null

    override fun loadDataByRefresh() {
        userRepository.getPersonInfo(null, this, login)
    }

    override fun loadDataByLoadMore() {
        if (getUserModel().type == "Organization") {
            userRepository.getOrgMembers(login!!, this, page)
        } else {
            userRepository.getUserEvent(login!!, this, page)
        }
    }

    fun onTabIconClick(v: View?) {
        getUserModel().login?.apply {
            when(v?.id) {
                R.id.user_header_repos -> {
                    v.context.toast("仓库")
                }
                R.id.user_header_fan -> {
                    v.context.toast("粉丝")
                }
                R.id.user_header_focus -> {
                    v.context.toast("关注")
                }
                R.id.user_header_star -> {
                    v.context.toast("星标")
                }
                R.id.user_header_honor -> {
                    v.context.toast(R.string.user100Honor)
                }
            }
        }
    }

    abstract fun getUserModel(): UserUIModel

    open fun onFocusClick(v: View?) {

    }

}