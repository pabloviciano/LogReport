package com.alavpa.logreport

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alavpa.logger.LogActivity
import com.alavpa.logger.Logger

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //startActivity(Intent(this, LogActivity::class.java))
    }
}
