package com.lovelz.mykotlinproject.model

import com.lovelz.mykotlinproject.model.ui.UserUIModel
import javax.inject.Inject

/**
 * @author lovelz
 * @date on 2018/11/28.
 */
class AppGlobalModel @Inject constructor() {
    val userObservable = UserUIModel()
}