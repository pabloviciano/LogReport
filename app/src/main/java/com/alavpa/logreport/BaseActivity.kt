package com.alavpa.logreport

import android.support.v7.app.AppCompatActivity
import com.alavpa.logger.Logger

open class BaseActivity : AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        Logger(layoutResID, this, ::setParentContentView)
    }

    private fun setParentContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
    }
}
