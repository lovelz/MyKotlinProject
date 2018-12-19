package com.lovelz.mykotlinproject.module.dynamic

import android.app.Application
import com.lovelz.mykotlinproject.module.base.BaseViewModel
import com.lovelz.mykotlinproject.repository.UserRepository
import javax.inject.Inject

/**
 * @author lovelz
 * @date on 2018/12/5.
 */
class DynamicViewModel @Inject constructor(private val userRepository: UserRepository, application: Application) : BaseViewModel(application) {

    override fun refresh() {
        if (isLoading()) {
            return
        }
        super.refresh()
    }

    override fun loadDataByRefresh() {
        loadData()
    }

    override fun loadDataByLoadMore() {
        loadData()
    }

    private fun loadData() {
        clearWhenRefresh()
        userRepository.getReceivedEvent(this, page)
    }
}