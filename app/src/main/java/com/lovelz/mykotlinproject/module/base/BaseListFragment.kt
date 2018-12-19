package com.lovelz.mykotlinproject.module.base

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.lovelz.mykotlinproject.model.ui.EmptyUIModel
import com.lovelz.mykotlinproject.ui.holder.EmptyHolder
import com.lovelz.mykotlinproject.ui.holder.base.BindCustomLoadMoreFooter
import com.lovelz.mykotlinproject.ui.holder.base.BindCustomRefreshHeader
import com.lovelz.mykotlinproject.ui.holder.base.BindingDataRecyclerManager
import com.shuyu.commonrecycler.BindSuperAdapter
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.shuyu.commonrecycler.listener.OnItemClickListener
import com.shuyu.commonrecycler.listener.OnLoadingListener
import javax.inject.Inject

/**
 * 基础列表
 * @author lovelz
 * @date on 2018/12/3.
 */
abstract class BaseListFragment<T: ViewDataBinding, R: BaseViewModel> : BaseFragment<T>(), OnItemClickListener, OnLoadingListener {

    protected var normalAdapterManager by autoCleared<BindingDataRecyclerManager>()

    protected lateinit var baseViewModel: R

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var adapter by autoCleared<BindSuperAdapter>()

    override fun onCreateView(mainView: View?) {
        normalAdapterManager = BindingDataRecyclerManager()
        baseViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(getViewModelClass())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()

        getViewModel().loading.observe(this, Observer {
            when(it) {
                LoadState.RefreshDone -> {
                    refreshComplete()
                }
                LoadState.LoadMoreDone -> {
                    loadMoreComplete()
                }
                LoadState.Refresh -> {

                }
            }
        })

        getViewModel().dataList.observe(this, Observer { items ->
            items?.apply {
                if (items.size > 0) {
                    if (getViewModel().isFirstData()) {
                        adapter?.dataList?.clear()
                    }
                    val currentSize: Int = adapter?.dataList?.size ?: 0
                    adapter?.dataList?.addAll(items)
                    if (currentSize == 0) {
                        notifyChanged()
                    } else {
                        notifyInsert(currentSize, items.size)
                    }
                } else {
                    if (getViewModel().isFirstData()) {
                        adapter?.dataList?.clear()
                    }
                }
            }
        })

        getViewModel().needMore.observe(this, Observer {
            it?.apply {
                normalAdapterManager?.setNoMore(!it)
            }
        })

        showRefresh()
    }

    /**
     * item 点击
     * @param context Context
     * @param position Int
     */
    override fun onItemClick(context: Context, position: Int) {

    }

    /**
     * 刷新
     */
    override fun onRefresh() {
        getViewModel().refresh()
    }

    /**
     * 加载更多
     */
    override fun onLoadMore() {
        getViewModel().loadMore()
    }

    /**
     * ViewModule Class
     */
    abstract fun getViewModelClass(): Class<R>

    /**
     * 当前RecyclerView
     * @return RecyclerView?
     */
    abstract fun getRecyclerView(): RecyclerView?

    /**
     * 绑定Item
     * @param manager BindSuperAdapterManager
     */
    abstract fun bindHolder(manager: BindSuperAdapterManager)

    /**
     * ViewModel
     * @return R
     */
    open fun getViewModel(): R = baseViewModel

    /**
     * 是否需要下拉刷新
     * @return Boolean
     */
    open fun enableRefresh(): Boolean = false

    /**
     * 是否需要加载更多
     * @return Boolean
     */
    open fun enableLoadMore(): Boolean = false

    open fun refreshComplete() {
        normalAdapterManager?.refreshComplete()
    }

    open fun loadMoreComplete() {
        normalAdapterManager?.loadMoreComplete()
    }

    open fun isLoading(): Boolean = getViewModel().isLoading()
    /**
     * 刷新
     */
    open fun notifyChanged() {
        adapter?.notifyDataSetChanged()
    }

    open fun notifyInsert(position: Int, count: Int) {
        adapter?.notifyItemRangeInserted(position + adapter!!.absFirstPosition(), count)
    }

    open fun notifyDelete(position: Int, count: Int) {
        adapter?.dataList?.removeAt(position)
        adapter?.notifyItemRangeRemoved(position + adapter!!.absFirstPosition(), count)
    }

    open fun showRefresh() {
        normalAdapterManager?.setRefreshing(true)
    }

    private fun initList() {
        if (activity != null && getRecyclerView() != null) {
            normalAdapterManager?.setPullRefreshEnabled(enableRefresh())
                    ?.setLoadingMoreEnabled(enableLoadMore())
                    ?.setOnItemClickListener(this)
                    ?.setLoadingListener(this)
                    ?.setRefreshHeader(BindCustomRefreshHeader(activity!!))
                    ?.setFootView(BindCustomLoadMoreFooter(activity!!))
                    ?.setLoadingMoreEmptyEnabled(false)
                    ?.bindEmpty(EmptyUIModel(), EmptyHolder.ID, EmptyHolder::class.java)

            normalAdapterManager?.apply {
                bindHolder(this)
                adapter = BindSuperAdapter(activity as Context, this, arrayListOf())
                getRecyclerView()?.layoutManager = LinearLayoutManager(activity!!)
                getRecyclerView()?.adapter = adapter
            }
        }
    }

}