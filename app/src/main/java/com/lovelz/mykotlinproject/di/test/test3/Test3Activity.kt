package com.lovelz.mykotlinproject.di.test.test3

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.utils.Debuger
import javax.inject.Inject

/**
 * @author lovelz
 * @date on 2018/12/7.
 */
class Test3Activity : AppCompatActivity() {

    @Inject
    lateinit var pot: Pot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_test)

        DaggerTest3ActivityComponent.builder()
                .potComponent(DaggerPotComponent.builder()
                        .flowerComponent(DaggerFlowerComponent.create())
                        .build())
                .build()
                .inject(this)

        Debuger.printLog("${pot.show()} 00")
    }

}