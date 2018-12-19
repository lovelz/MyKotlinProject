package com.lovelz.mykotlinproject.di.test.test2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.utils.Debuger
import javax.inject.Inject

/**
 * @author lovelz
 * @date on 2018/12/6.
 */
class Test2Activity : AppCompatActivity() {

    @Inject
    lateinit var flower: RoseLily

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_test)

        DaggerTest2ActivityComponent.create().inject(this)
        val aa = flower.show()
        Debuger.printLog("$aa 00")
    }

}