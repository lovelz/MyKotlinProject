package com.lovelz.mykotlinproject.net

import com.lovelz.mykotlinproject.AppConfig
import com.lovelz.mykotlinproject.BuildConfig
import com.lovelz.mykotlinproject.utils.Debuger
import com.lovelz.mykotlinproject.utils.LZPreference
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 网络请求
 *
 * @author lovelz
 * @date on 2018/11/24.
 */
class RetrofitFactory private constructor() {

    private var accessTokenStorage: String by LZPreference(AppConfig.ACCESS_TOKEN, "")

    private var accessBasicCodeStorage: String by LZPreference(AppConfig.USER_BASIC_INFO, "")

    val retrofit: Retrofit

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else{
            HttpLoggingInterceptor.Level.NONE
        }

        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(AppConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(headerInterceptor())
                .addInterceptor(PageInfoInterceptor())
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl(AppConfig.GITHUB_API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private fun headerInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()

            val accessToken = getAuthorization()
            Debuger.printLog("headerInterceptor", accessToken)

            if (!accessToken.isEmpty()) {
                val url = request.url().toString()
                request = request.newBuilder()
                        .addHeader("Authorization", accessToken)
                        .url(url)
                        .build()
            }

            chain.proceed(request)
        }
    }

    /**
     * 获取token
     */
    fun getAuthorization(): String {
        if (accessTokenStorage.isBlank()) {
            val basic = accessBasicCodeStorage
            return if (basic.isBlank()) {
                ""
            } else{
                "Basic $basic"
            }
        }
        return "token $accessTokenStorage"
    }

    companion object {

        @Volatile
        private var mRetrofitFactory: RetrofitFactory? = null

        val instance: RetrofitFactory
            get() {
                if (mRetrofitFactory == null) {
                    synchronized(RetrofitFactory::class.java) {
                        if (mRetrofitFactory == null) {
                            mRetrofitFactory = RetrofitFactory()
                        }
                    }
                }
                return mRetrofitFactory!!
            }

        fun <T> createService(service: Class<T>): T {
            return instance.retrofit.create(service)
        }

        fun <T> executeResult(observable: Observable<Response<T>>, subscriber: ResultObserver<T>) {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber)
        }
    }
}