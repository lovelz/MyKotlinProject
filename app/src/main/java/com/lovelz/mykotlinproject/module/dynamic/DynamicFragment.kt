package com.lovelz.mykotlinproject.module.dynamic

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.databinding.FragmentListBinding
import com.lovelz.mykotlinproject.model.ui.EventUIModel
import com.lovelz.mykotlinproject.module.base.BaseListFragment
import com.lovelz.mykotlinproject.ui.holder.EventHolder
import com.lovelz.mykotlinproject.utils.EventUtils
import com.shuyu.commonrecycler.BindSuperAdapterManager
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * 动态
 *
 * @author lovelz
 * @date on 2018/12/3.
 */
class DynamicFragment : BaseListFragment<FragmentListBinding, DynamicViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun getViewModelClass(): Class<DynamicViewModel> = DynamicViewModel::class.java

    override fun getRecyclerView(): RecyclerView? = baseRecycler

    override fun bindHolder(manager: BindSuperAdapterManager) {
        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
    }

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)
        EventUtils.eventAction(activity, adapter?.dataList?.get(position) as EventUIModel)
    }

}