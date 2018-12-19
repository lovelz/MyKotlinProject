package com.lovelz.mykotlinproject.di

import android.app.Application
import com.lovelz.mykotlinproject.App
import com.lovelz.mykotlinproject.db.RealmFactory
import com.lovelz.mykotlinproject.net.RetrofitFactory
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author lovelz
 * @date on 2018/11/24.
 */

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBindModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun providerRetrofit(): Retrofit {
        return RetrofitFactory.instance.retrofit
    }

    @Singleton
    @Provides
    fun providerRealmFactory(): RealmFactory {
        return RealmFactory.instance
    }

}