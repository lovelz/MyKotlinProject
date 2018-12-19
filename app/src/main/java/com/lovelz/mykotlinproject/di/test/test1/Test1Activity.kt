package com.lovelz.mykotlinproject.di.test.test1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lovelz.mykotlinproject.R
import com.lovelz.mykotlinproject.utils.Debuger
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * @author lovelz
 * @date on 2018/12/6.
 */
class Test1Activity : AppCompatActivity() {

    @Inject
    lateinit var pot: Pot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_test)

        DaggerTest1ActivityComponent.create().inject(this)
        val show = pot.show()
        toast("$show  11")
        Debuger.printLog(Test1Activity::class.java.simpleName, "$show  11")
    }

}