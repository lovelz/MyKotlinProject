package com.lovelz.mykotlinproject.di.test.test1

import dagger.Component

/**
 * @author lovelz
 * @date on 2018/12/6.
 */
@Component
interface Test1ActivityComponent {

    fun inject(test1Activity: Test1Activity)

}