package com.lovelz.mykotlinproject.model

import com.lovelz.mykotlinproject.model.ui.UserUIModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author lovelz
 * @date on 2018/11/28.
 */
@Singleton
class AppGlobalModel @Inject constructor() {
    val userObservable = UserUIModel()
}