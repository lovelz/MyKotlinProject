package com.lovelz.mykotlinproject.di.test.test2

import dagger.Component

/**
 * @author lovelz
 * @date on 2018/12/6.
 */
@Component(modules = [FlowerModule::class])
interface Test2ActivityComponent {

    fun inject(test2Activity: Test2Activity)

}