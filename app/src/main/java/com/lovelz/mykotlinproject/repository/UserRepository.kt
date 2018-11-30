package com.lovelz.mykotlinproject.repository

import android.app.Application
import com.lovelz.mykotlinproject.AppConfig
import com.lovelz.mykotlinproject.model.AppGlobalModel
import com.lovelz.mykotlinproject.model.bean.User
import com.lovelz.mykotlinproject.model.conversion.UserConversion
import com.lovelz.mykotlinproject.net.FlatMapResponse2Result
import com.lovelz.mykotlinproject.repository.dao.UserDao
import com.lovelz.mykotlinproject.service.UserService
import com.lovelz.mykotlinproject.utils.Debuger
import com.lovelz.mykotlinproject.utils.GsonUtils
import com.lovelz.mykotlinproject.utils.LZPreference
import io.reactivex.Observable
import io.reactivex.functions.Function
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * 获取用户相关数据
 *
 * @author lovelz
 * @date on 2018/11/29.
 */
class UserRepository @Inject constructor(private val retrofit: Retrofit, private val appGlobalModel: AppGlobalModel,
                                             private val application: Application, private val userDao: UserDao) {

    /**
     * 登录信息SharePreference委托
     */
    private var userInfoStorage: String by LZPreference(AppConfig.USER_INFO, "")

    fun getPersonInfoObservable(username: String? = null): Observable<User> {
        val isLoginUser = username == null
        //根据是否有用户名，获取第三方用户数据或者当前用户数据
        val userService = if (isLoginUser) {
            retrofit.create(UserService::class.java).getPersonInfo(true)
        } else {
            retrofit.create(UserService::class.java).getUser(true, username!!)
        }
        return doUserInfoFlat(userService, isLoginUser)
    }

    /**
     * 用户数据请求、保存
     *
     * @param userService Observable<Response<User>>
     * @param isLoginUser Boolean
     * @return Observable<User>
     */
    private fun doUserInfoFlat(userService: Observable<Response<User>>, isLoginUser: Boolean): Observable<User> {
        return userService.flatMap {
            FlatMapResponse2Result(it)
        }.doOnNext {
            if (isLoginUser) {
                userInfoStorage = GsonUtils.jsonToString(it)
                UserConversion.cloneDataFromUser(application, it, appGlobalModel.userObservable)
            }
        }.onErrorResumeNext(Function<Throwable, Observable<User>> {
            Debuger.printLog("userInfo onErrorResumeNext")
            Observable.error(it)
        })
    }

}