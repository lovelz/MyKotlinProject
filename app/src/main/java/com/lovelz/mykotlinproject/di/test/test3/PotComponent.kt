package com.lovelz.mykotlinproject.di.test.test3

import dagger.Component

/**
 * @author lovelz
 * @date on 2018/12/7.
 */
@Component(modules = [PotModule::class], dependencies = [FlowerComponent::class])
interface PotComponent {

    fun getPot(): Pot

}