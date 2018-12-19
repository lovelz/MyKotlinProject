package com.lovelz.mykotlinproject.repository

import android.app.Application
import android.content.Context
import com.lovelz.mykotlinproject.AppConfig
import com.lovelz.mykotlinproject.model.AppGlobalModel
import com.lovelz.mykotlinproject.model.bean.Event
import com.lovelz.mykotlinproject.model.bean.User
import com.lovelz.mykotlinproject.model.conversion.EventConversion
import com.lovelz.mykotlinproject.model.conversion.UserConversion
import com.lovelz.mykotlinproject.net.*
import com.lovelz.mykotlinproject.repository.dao.UserDao
import com.lovelz.mykotlinproject.service.RepoService
import com.lovelz.mykotlinproject.service.UserService
import com.lovelz.mykotlinproject.utils.Debuger
import com.lovelz.mykotlinproject.utils.GsonUtils
import com.lovelz.mykotlinproject.utils.LZPreference
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import okhttp3.ResponseBody
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
     * 获取用户信息
     */
    fun getPersonInfo(resultCallBack: ResultCallBack<User>?, resultEventCallback: ResultCallBack<ArrayList<Any>>, userName: String? = null) {
        val userObserver = userDao.getUserInfoDao(userName)
                .doOnNext {
                    it?.login?.let { login ->
                        resultCallBack?.onCacheSuccess(it)
                    }
                }.flatMap {
                    if (it.login != null) {
                        if (it.type == "Organization") {
                            userDao.getOrgMembersDao(it.login!!)
                        } else {
                            userDao.getUserEventDao(it.login!!)
                        }
                    } else {
                        Observable.just(ArrayList())
                    }
                }.doOnNext {
                    resultEventCallback.onCacheSuccess(it)
                }

        val mergeService = getPersonInfoObservable(userName)
                .flatMap {
                    resultCallBack?.onSuccess(it)
                    if (it.type == "Organization") {
                        getOrgMembers(it.login!!, resultEventCallback)
                        Observable.just(Response.success(ArrayList()))
                    } else {
                        getUserEventObservable(it.login!!)
                    }
                }

        val zipService = Observable.zip(userObserver, mergeService,
                BiFunction<ArrayList<Any>, Response<ArrayList<Event>>, Response<ArrayList<Event>>> { _, remote ->
                    remote
                })

        userEventRequest(zipService, resultEventCallback)
    }

    /**
     * 获取用户产生的事件
     */
    fun getUserEvent(login: String?, resultCallBack: ResultCallBack<ArrayList<Any>>?, page: Int = 1) {
        val userName = login ?: ""
        if (userName.isEmpty()) {
            return
        }

        val userEvent = getUserEventObservable(login, page)
        userEventRequest(userEvent, resultCallBack)
    }

    /**
     * 获取用户关注事件
     */
    fun getOrgMembers(login: String?, resultCallBack: ResultCallBack<ArrayList<Any>>?, page: Int = 1) {
        val userName = login ?: ""
        if (userName.isEmpty()) {
            return
        }

        val userEvent = retrofit.create(UserService::class.java)
                .getOrgMembers(true, login ?: "", page)
                .doOnNext{
                    userDao.saveOrgMembersDao(it, login!!, page == 1)
                }.flatMap {
                    FlatMapResponse2ResponseResult(it, object : FlatConversionInterface<ArrayList<User>> {
                        override fun onConversion(t: ArrayList<User>?): ArrayList<Any> {
                            val dataList = ArrayList<Any>()
                            t?.apply {
                                for (data in t) {
                                    dataList.add(UserConversion.userToUserUIModel(data))
                                }
                            }
                            return dataList
                        }
                    })
                }

        RetrofitFactory.executeResult(userEvent, object : ResultTipObserver<ArrayList<Any>>(application) {
            override fun onPageInfo(first: Int, current: Int, last: Int) {
                resultCallBack?.onPage(first, current, last)
            }

            override fun onSuccess(result: ArrayList<Any>?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onCodeError(code: Int, message: String) {
                resultCallBack?.onFailure()
            }

            override fun onFailure(e: Throwable, isNetworkError: Boolean) {
                resultCallBack?.onFailure()
            }

        })
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
        }.flatMap {
            val starredService = retrofit.create(RepoService::class.java).getStarredRepos(true, it.login!!, 1, "updated", 1)
            val honorService = retrofit.create(RepoService::class.java).getUserRepository100StatusDao(true, it.login!!, 1)
            val starResponse = starredService.blockingSingle()
            val honorResponse = honorService.blockingSingle()

            val starPageString = starResponse.headers().get("page_info")
            starPageString ?.let { pageString ->
                val pageInfo = GsonUtils.parseJsonToBean(pageString, PageInfoInterceptor.PageInfo::class.java)
                it.starRepos = if (pageInfo.last < 0) {
                    0
                } else {
                    pageInfo.last
                }
            }

            if (honorResponse.isSuccessful) {
                val list = honorResponse.body()
                var count = 0
                list?.forEach {
                    count += it.watchersCount
                }
                it.honorRepos = count
            }

            Observable.just(it)
        }.doOnNext {
            if (isLoginUser) {
                userInfoStorage = GsonUtils.jsonToString(it)
                UserConversion.cloneDataFromUser(application, it, appGlobalModel.userObservable)
            }
            userDao.saveUserInfo(Response.success(it), it.login!!)
        }.onErrorResumeNext(Function<Throwable, Observable<User>> {
            Debuger.printLog("userInfo onErrorResumeNext")
            Observable.error(it)
        })
    }

    /**
     * 获取用户接收到的事件
     */
    fun getReceivedEvent(resultCallBack: ResultCallBack<ArrayList<Any>>?, page: Int = 1) {
        val login = appGlobalModel.userObservable.login
        val username = login ?: ""
        if (username.isEmpty()) {
            return
        }

        var receivedEvent = retrofit.create(UserService::class.java)
                .getNewsEvent(true, username, page)
                .doOnNext {
                    userDao.saveReceivedEventDao(it, page == 1)
                }

        val userObserver = userDao.getReceivedEventDao()
                .doOnNext {
                    if (page == 1) {
                        resultCallBack?.onCacheSuccess(it)
                    }
                }

        val zipService = Observable.zip(userObserver, receivedEvent,
                BiFunction<ArrayList<Any>, Response<ArrayList<Event>>, Response<ArrayList<Event>>> {_, remote->
                    remote
                })

        userEventRequest(zipService, resultCallBack)
    }

    /**
     * 用户事件请求
     */
    private fun userEventRequest(observer: Observable<Response<ArrayList<Event>>>, resultCallBack: ResultCallBack<ArrayList<Any>>?) {
        val service = observer
                .flatMap {
                    FlatMapResponse2ResponseResult(it, object : FlatConversionInterface<ArrayList<Event>> {
                        override fun onConversion(t: ArrayList<Event>?): ArrayList<Any> {
                            val eventUIList = ArrayList<Any>()
                            t?.apply {
                                for (event in t) {
                                    eventUIList.add(EventConversion.eventToEventUIModel(event))
                                }
                            }
                            return eventUIList
                        }

                    })
                }

        RetrofitFactory.executeResult(service, object : ResultTipObserver<ArrayList<Any>>(application) {
            override fun onPageInfo(first: Int, current: Int, last: Int) {
                resultCallBack?.onPage(first, current, last)
            }

            override fun onSuccess(result: ArrayList<Any>?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onCodeError(code: Int, message: String) {
                resultCallBack?.onFailure()
            }

            override fun onFailure(e: Throwable, isNetworkError: Boolean) {
                resultCallBack?.onFailure()
            }

        })
    }

    /**
     * 获取用户产生的行为事件
     */
    fun getUserEventObservable(username: String?, page: Int = 1): Observable<Response<ArrayList<Event>>> {
        return retrofit.create(UserService::class.java)
                .getUserEvent(true, username ?: "", page)
                .doOnNext {
                    userDao.saveUserEventDao(it, username!!, page == 1)
                }
    }

    /**
     * 检查是否关注
     */
    fun checkFocus(userName: String?, resultCallBack: ResultCallBack<Boolean>?) {
        userName?.apply {
            Debuger.printLog(this + "------" + appGlobalModel.userObservable.login)
            if (this == appGlobalModel.userObservable.login) {
                return@apply
            }

            val service = retrofit.create(UserService::class.java)
                    .checkFollowing(userName)
                    .flatMap {
                        val watched = if (it.code() == 404) {
                            false
                        } else it.isSuccessful

                        Observable.just(watched)
                    }.flatMap {
                        FlatMapResult2Response(it)
                    }

            RetrofitFactory.executeResult(service, object : ResultObserver<Boolean>() {
                override fun onSuccess(result: Boolean?) {
                    resultCallBack?.onSuccess(result)
                }

                override fun onFailure(e: Throwable, isNetworkError: Boolean) {
                    resultCallBack?.onFailure()
                }
            })
        }
    }

    /**
     * 关注 or 取消关注
     */
    fun doFocus(context: Context, userName: String?, isFocus: Boolean = false, resultCallBack: ResultCallBack<Boolean>?) {
        userName?.apply {
            if (this == appGlobalModel.userObservable.login) {
                return@apply
            }

            val service = if (isFocus) {
                retrofit.create(UserService::class.java)
                        .unFollowUser(userName)
            } else {
                retrofit.create(UserService::class.java)
                        .followUser(userName)
            }

            RetrofitFactory.executeResult(service, object : ResultProgressObserver<ResponseBody>(context) {
                override fun onSuccess(result: ResponseBody?) {
                    resultCallBack?.onSuccess(true)
                }

                override fun onFailure(e: Throwable, isNetworkError: Boolean) {

                }
            })
        }
    }

}