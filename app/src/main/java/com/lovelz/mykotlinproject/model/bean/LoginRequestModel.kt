package com.lovelz.mykotlinproject.model.bean

import com.google.gson.annotations.SerializedName
import com.lovelz.mykotlinproject.BuildConfig
import java.util.*

/**
 * 请求登录的model对象
 *
 * @author lovelz
 * @date on 2018/11/29.
 */
class LoginRequestModel {

    var scopes: List<String>? = null
        private set

    var note: String? = null
        private set

    @SerializedName("client_id")
    var clientId: String? = null
        private set

    @SerializedName("client_secret")
    var clientSecret: String? = null
        private set

    companion object {
        fun generate(): LoginRequestModel {
            val model = LoginRequestModel()
            model.scopes = Arrays.asList("user", "repo", "gist", "notifications")
            model.note = BuildConfig.APPLICATION_ID
            model.clientId = BuildConfig.CLIENT_ID
            model.clientSecret = BuildConfig.CLIENT_SECRET
            return model
        }
    }

}