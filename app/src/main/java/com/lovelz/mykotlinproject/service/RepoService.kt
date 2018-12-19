package com.lovelz.mykotlinproject.service

import com.lovelz.mykotlinproject.AppConfig
import com.lovelz.mykotlinproject.model.bean.Repository
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author lovelz
 * @date on 2018/12/1.
 */
interface RepoService {

    @GET("users/{user}/repos")
    fun getUserRepository100StatusDao(@Header("forceNetWork") forceNetWork: Boolean,
                                      @Path("user") user: String,
                                      @Query("page") page: Int,
                                      @Query("sort") sort: String = "pushed",
                                      @Query("per_page") per_page: Int = 100
    ): Observable<Response<ArrayList<Repository>>>

    @GET("users/{user}/starred")
    fun getStarredRepos(@Header("forceNetWork") forceNetWork: Boolean,
                        @Path("user") user: String,
                        @Query("page") page: Int,
                        @Query("sort") sort: String = "updated",
                        @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<Repository>>>

}