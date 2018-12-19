package com.lovelz.mykotlinproject.repository

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.util.Base64
import com.lovelz.mykotlinproject.AppConfig
import com.lovelz.mykotlinproject.model.bean.LoginRequestModel
import com.lovelz.mykotlinproject.model.bean.User
import com.lovelz.mykotlinproject.module.StartNavigationActivity
import com.lovelz.mykotlinproject.net.FlatMapResponse2Result
import com.lovelz.mykotlinproject.net.FlatMapResult2Response
import com.lovelz.mykotlinproject.net.ResultProgressObserver
import com.lovelz.mykotlinproject.net.RetrofitFactory
import com.lovelz.mykotlinproject.service.LoginService
import com.lovelz.mykotlinproject.utils.Debuger
import com.lovelz.mykotlinproject.utils.LZPreference
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import org.jetbrains.anko.clearTask
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * 登录数据仓库对象
 *
 * @author lovelz
 * @date on 2018/11/29.
 */
class LoginRepository @Inject constructor(private val retrofit: Retrofit, private val userRepository: UserRepository) {

    /**
     * 登录用户信息的相关委托
     */
    private var usernameStorage: String by LZPreference(AppConfig.USER_NAME, "")

    private var passwordStorage: String by LZPreference(AppConfig.USER_PASSWORD, "")

    private var accessTokenStorage: String by LZPreference(AppConfig.ACCESS_TOKEN, "")

    private var userBasicCodeStorage: String by LZPreference(AppConfig.USER_BASIC_CODE, "")

    private var userInfoStorage: String by LZPreference(AppConfig.USER_INFO, "")

    fun getTokenObservable(): Observable<String> {
        return retrofit.create(LoginService::class.java)
                .authorizations(LoginRequestModel.generate())
                .flatMap {
                    FlatMapResponse2Result(it)
                }.map {
                    it.token ?: ""
                }.doOnNext {
                    Debuger.printLog("token :$it")
                    accessTokenStorage = it
                }.onErrorResumeNext(Function<Throwable, Observable<String>> {
                    Debuger.printLog("token onErrorResumeNext!")
                    clearTokenStorage()
                    Observable.error(it)
                })
    }

    /**
     * 登录
     * @param context Context
     * @param username String
     * @param password String
     * @param token MutableLiveData<Boolean>
     */
    fun login(context: Context, username: String, password: String, token: MutableLiveData<Boolean>) {

        clearTokenStorage()

        val type = "$username:$password"

        val base64 = Base64.encodeToString(type.toByteArray(), Base64.NO_WRAP).replace("\\+", "%2B")

        Debuger.printLog("base64 login $base64")

        usernameStorage = username

        userBasicCodeStorage = base64

        val loginService = getTokenObservable()

        val userService = userRepository.getPersonInfoObservable()

        //将多个Observable对象结合到一起
        val authorizations = Observable.zip(loginService, userService,
                BiFunction<String, User, User> {_, user ->
                    user
                }).flatMap {
            FlatMapResult2Response(it)
        }

        RetrofitFactory.executeResult(authorizations, object : ResultProgressObserver<User>(context) {
            override fun onSuccess(result: User?) {
                passwordStorage = password
                token.value = true
            }

            override fun onCodeError(code: Int, message: String) {
                token.value = false
            }

            override fun onFailure(e: Throwable, isNetworkError: Boolean) {
                token.value = false
            }
        })
    }

    /**
     * 清除token信息
     */
    fun clearTokenStorage() {
        accessTokenStorage = ""
        userBasicCodeStorage = ""
    }

    /**
     * 退出登录
     * @param context Context
     */
    fun logout(context: Context) {
        userBasicCodeStorage = ""
        accessTokenStorage = ""
        userInfoStorage = ""
        val intent = Intent(context, StartNavigationActivity::class.java)
        intent.clearTask()
        context.startActivity(intent)
    }

}