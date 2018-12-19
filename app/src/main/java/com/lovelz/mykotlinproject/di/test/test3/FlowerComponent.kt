package com.lovelz.mykotlinproject.di.test.test3

import dagger.Component

/**
 * @author lovelz
 * @date on 2018/12/7.
 */
@Component(modules = [FlowerModule::class])
interface FlowerComponent {

    @RoseFlower
    fun getRoseFlower(): Flower

    @LilyFlower
    fun getLilyFlower(): Flower

}