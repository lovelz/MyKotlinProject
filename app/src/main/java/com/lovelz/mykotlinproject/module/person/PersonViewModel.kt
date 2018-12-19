package com.lovelz.mykotlinproject.module.person

import android.app.Application
import android.view.View
import com.lovelz.mykotlinproject.model.bean.User
import com.lovelz.mykotlinproject.model.conversion.UserConversion
import com.lovelz.mykotlinproject.model.ui.UserUIModel
import com.lovelz.mykotlinproject.module.base.BaseUserInfoViewModel
import com.lovelz.mykotlinproject.net.ResultCallBack
import com.lovelz.mykotlinproject.repository.UserRepository
import com.lovelz.mykotlinproject.utils.Debuger
import javax.inject.Inject

/**
 * @author lovelz
 * @date on 2018/12/11.
 */
class PersonViewModel @Inject constructor(private val userRepository: UserRepository, private val application: Application) : BaseUserInfoViewModel(userRepository, application) {

    val userObservable = UserUIModel()

    private var isFocus = false

    override fun loadDataByRefresh() {
        userRepository.getPersonInfo(object : ResultCallBack<User> {
            override fun onCacheSuccess(result: User?) {
                result?.apply {
                    UserConversion.cloneDataFromUser(application, this, userObservable)
                    if (userObservable.type == "Organization") {
                        return
                    }
                    Debuger.printLog(this.login)
                    checkFocus(this.login)
                }
            }

            override fun onSuccess(result: User?) {
                result?.apply {
                    UserConversion.cloneDataFromUser(application, this, userObservable)
                }
            }

            override fun onFailure() {

            }

        }, this, login)
    }

    private fun checkFocus(login: String?) {
        userRepository.checkFocus(login, object : ResultCallBack<Boolean> {
            override fun onSuccess(result: Boolean?) {
                result?.apply {
                    isFocus = result
                    focusIcon.set(getFocusIcon())
                }
            }
        })
    }

    override fun onFocusClick(v: View?) {
        userRepository.doFocus(v!!.context, userObservable.login, isFocus, object : ResultCallBack<Boolean> {
            override fun onSuccess(result: Boolean?) {
                isFocus = isFocus.not()
                focusIcon.set(getFocusIcon())
            }
        })
    }

    private fun getFocusIcon(): String {
        return if (isFocus) {
            "GSY-FOCUS"
        } else {
            "GSY-UN_FOCUS"
        }
    }

    override fun getUserModel(): UserUIModel = userObservable


}