package com.lovelz.mykotlinproject.di.test.test3

import dagger.Module
import dagger.Provides

/**
 * @author lovelz
 * @date on 2018/12/7.
 */
@Module
class PotModule {

    @Provides
    fun providePot(@RoseFlower flower: Flower): Pot {
        return Pot(flower)
    }

}