package com.lovelz.mykotlinproject.service

import com.lovelz.mykotlinproject.model.bean.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * @author lovelz
 * @date on 2018/11/30.
 */
interface UserService {

    @GET("user")
    fun getPersonInfo(@Header("forceNetWork") forceNetWork: Boolean): Observable<Response<User>>

    @GET("users/{user}")
    fun getUser(@Header("forceNetWork") forceNetWork: Boolean,
                @Path("user") user: String): Observable<Response<User>>

}