package com.lovelz.mykotlinproject.service

import com.lovelz.mykotlinproject.AppConfig
import com.lovelz.mykotlinproject.model.bean.Event
import com.lovelz.mykotlinproject.model.bean.User
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * @author lovelz
 * @date on 2018/11/30.
 */
interface UserService {

    @GET("user")
    fun getPersonInfo(@Header("forceNetWork") forceNetWork: Boolean
    ): Observable<Response<User>>

    @GET("users/{user}")
    fun getUser(@Header("forceNetWork") forceNetWork: Boolean,
                @Path("user") user: String
    ): Observable<Response<User>>

    @GET("users/{user}/events")
    fun getUserEvent(@Header("forceNetWork") forceNetWork: Boolean,
                     @Path("user") user: String,
                     @Query("page") page: Int,
                     @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<Event>>>

    @GET("users/{user}/received_events")
    fun getNewsEvent(@Header("forceNetWork") forceNetWork: Boolean,
                     @Path("user") user: String,
                     @Query("page") page: Int,
                     @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<Event>>>

    @GET("orgs/{org}/members")
    fun getOrgMembers(@Header("forceNetWork") forceNetWork: Boolean,
                      @Path("org") org: String,
                      @Query("page") page: Int,
                      @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<User>>>

    @GET("user/following/{user}")
    fun checkFollowing(@Path("user") user: String
    ): Observable<Response<ResponseBody>>

    @GET("users/{user}/following/{targetUser}")
    fun checkFollowing(@Path("user") user: String,
                       @Path("targetUser") targetUser: String
    ): Observable<Response<ResponseBody>>

    @PUT("user/following/{user}")
    fun followUser(@Path("user") user: String
    ): Observable<Response<ResponseBody>>

    @DELETE("user/following/{user}")
    fun unFollowUser(@Path("user") user: String
    ): Observable<Response<ResponseBody>>

}