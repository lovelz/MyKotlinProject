package com.lovelz.mykotlinproject

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.alibaba.android.arouter.launcher.ARouter
import com.lovelz.mykotlinproject.db.RealmFactory
import com.lovelz.mykotlinproject.di.AppInjector
import com.lovelz.mykotlinproject.style.MyIconFont
import com.lovelz.mykotlinproject.utils.CommonUtils
import com.mikepenz.iconics.Iconics
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.shuyu.gsygiideloader.GSYGlideImageLoader
import com.shuyu.gsyimageloader.GSYImageLoaderManager
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * @author lovelz
 * @date on 2018/11/20.
 */
class App : Application(), HasActivityInjector {

    /**
     * 单例实现
     */
    companion object {
        var instance: App by Delegates.notNull()
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        instance = this

        //初始化路由
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)

        //Application级别注入
        AppInjector.init(this)

        //初始化图标库
        Iconics.init(this)
        Iconics.registerFont(MyIconFont())

        //初始化图片加载
        GSYImageLoaderManager.initialize(GSYGlideImageLoader(this))

        //初始化数据库
        Realm.init(this)
        RealmFactory.instance

        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {
            override fun placeholder(ctx: Context?): Drawable {
                return ContextCompat.getDrawable(this@App, R.mipmap.ic_launcher)!!
            }

            override fun set(imageView: ImageView?, uri: Uri?, placeholder: Drawable?) {
                CommonUtils.loadUserHeaderImage(imageView!!, uri.toString())
            }
        })
    }

}