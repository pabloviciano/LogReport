package com.alavpa.logreport

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alavpa.logger.Logger

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger(R.layout.activity_main, this)
    }
}
