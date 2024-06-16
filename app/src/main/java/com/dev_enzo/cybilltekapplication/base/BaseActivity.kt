package com.dev_enzo.cybilltekapplication.base

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.dev_enzo.data.network.HttpClient
import com.dev_enzo.data.network.HttpUtil
import com.dev_enzo.data.repository.DatabaseUtil

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        init()
    }

    public override fun onResume() {
        super.onResume()
    }

    public override fun onPause() {
        super.onPause()
    }

    public override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * Initialize the instances
     */
    protected fun init() {
        HttpClient.getInstance()
        HttpUtil.getInstance(this)
        DatabaseUtil.getInstance(this)
    }
}