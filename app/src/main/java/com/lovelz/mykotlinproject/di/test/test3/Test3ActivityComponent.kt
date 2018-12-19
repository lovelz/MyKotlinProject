package com.lovelz.mykotlinproject.di.test.test3

import dagger.Component

/**
 * @author lovelz
 * @date on 2018/12/7.
 */
@Component(dependencies = [PotComponent::class])
interface Test3ActivityComponent {
    fun inject(test3Activity: Test3Activity)
}