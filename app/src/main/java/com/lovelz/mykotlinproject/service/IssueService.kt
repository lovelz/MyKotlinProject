package com.lovelz.mykotlinproject.service

import com.lovelz.mykotlinproject.AppConfig
import com.lovelz.mykotlinproject.model.bean.CommentRequestModel
import com.lovelz.mykotlinproject.model.bean.Issue
import com.lovelz.mykotlinproject.model.bean.IssueEvent
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Issue接口API
 *
 * @author lovelz
 * @date on 2018/12/17.
 */
interface IssueService {

    @GET("repos/{owner}/{repo}/issues/{issueNumber}")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun getIssueInfo(@Header("forceNetWork") forceNetWork: Boolean,
                     @Path("owner") owner: String,
                     @Path("repo") repo: String,
                     @Path("issueNumber") issueNumber: Int
    ): Observable<Response<Issue>>


    @GET("repos/{owner}/{repo}/issues/{issueNumber}/comments")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun getIssueComments(@Header("forceNetWork") forceNetWork: Boolean,
                         @Path("owner") owner: String,
                         @Path("repo") repo: String,
                         @Path("issueNumber") issueNumber: Int,
                         @Query("page") page: Int,
                         @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<IssueEvent>>>


    @PATCH("repos/{owner}/{repo}/issues/{issueNumber}")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun editIssue(@Path("owner") owner: String,
                  @Path("repo") repo: String,
                  @Path("issueNumber") issueNumber: Int,
                  @Body body: Issue
    ): Observable<Response<Issue>>


    @POST("repos/{owner}/{repo}/issues")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun createIssue(@Path("owner") owner: String,
                    @Path("repo") repo: String,
                    @Body body: Issue
    ): Observable<Response<Issue>>


    @POST("repos/{owner}/{repo}/issues/{issueNumber}/comments")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun addComment(@Path("owner") owner: String,
                   @Path("repo") repo: String,
                   @Path("issueNumber") issueNumber: Int,
                   @Body body: CommentRequestModel
    ): Observable<Response<IssueEvent>>


    @PUT("repos/{owner}/{repo}/issues/{issueNumber}/lock")
    fun lockIssue(@Path("owner") owner: String,
                  @Path("repo") repo: String,
                  @Path("issueNumber") issueNumber: Int
    ): Observable<Response<ResponseBody>>


    @DELETE("repos/{owner}/{repo}/issues/{issueNumber}/lock")
    fun unLockIssue(@Path("owner") owner: String,
                    @Path("repo") repo: String,
                    @Path("issueNumber") issueNumber: Int
    ): Observable<Response<ResponseBody>>


    @PATCH("repos/{owner}/{repo}/issues/comments/{commentId}")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun editComment(@Path("owner") owner: String,
                    @Path("repo") repo: String,
                    @Path("commentId") commentId: String,
                    @Body body: CommentRequestModel
    ): Observable<Response<IssueEvent>>


    @DELETE("repos/{owner}/{repo}/issues/comments/{commentId}")
    fun deleteComment(@Path("owner") owner: String,
                      @Path("repo") repo: String,
                      @Path("commentId") commentId: String
    ): Observable<Response<ResponseBody>>

}