package com.lovelz.mykotlinproject.di.test.test2

import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * @author lovelz
 * @date on 2018/12/6.
 */
@Module
class FlowerModule {

//    @Provides
//    fun providerFlower(): Flower {
//        return Rose()
//    }

    @Provides
    @Named("Rose")
    fun providerRose(): Flower {
        return Rose()
    }

    @Named("Lily")
    @Provides
    fun providerLily(): Flower {
        return Lily()
    }

}