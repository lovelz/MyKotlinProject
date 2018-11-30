package com.lovelz.mykotlinproject.service

import com.lovelz.mykotlinproject.model.bean.AccessToken
import com.lovelz.mykotlinproject.model.bean.LoginRequestModel
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * @author lovelz
 * @date on 2018/11/29.
 */
interface LoginService {

    @POST("authorizations")
    @Headers("Accept: application/json")
    fun authorizations(@Body autoRequestModel: LoginRequestModel): Observable<Response<AccessToken>>

}