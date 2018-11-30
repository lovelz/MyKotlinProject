package com.lovelz.mykotlinproject.module

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lovelz.mykotlinproject.R
import org.jetbrains.anko.clearTask

/**
 * @author lovelz
 * @date on 2018/11/27.
 */
class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val intent = Intent(this, StartNavigationActivity::class.java)
        intent.clearTask()
        startActivity(intent)

        finish()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }

}