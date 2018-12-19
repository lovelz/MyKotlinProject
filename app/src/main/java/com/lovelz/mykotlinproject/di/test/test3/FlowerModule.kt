package com.lovelz.mykotlinproject.di.test.test3

import dagger.Module
import dagger.Provides

/**
 * @author lovelz
 * @date on 2018/12/7.
 */
@Module
class FlowerModule {

    @Provides
    @RoseFlower
    fun providerRose(): Flower {
        return Rose()
    }

    @Provides
    @LilyFlower
    fun providerLily(): Flower {
        return Lily()
    }

}