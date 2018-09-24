package com.mlabar.android_background_location_service.feature.main

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import com.mlabar.android_background_location_service.R
import com.mlabar.android_background_location_service.common.receiver.BootCompletedServiceReceiver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), View.OnClickListener {

    private val TAG = MainActivity::class.java.simpleName

    private val mBootCompletedServiceReceiver = BootCompletedServiceReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        button_start_service.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()

        registerReceiver(mBootCompletedServiceReceiver, IntentFilter("ACTION_START_SERVICE"))
    }

    override fun onPause() {
        super.onPause()

        unregisterReceiver(mBootCompletedServiceReceiver)
    }

    override fun onClick(v: View?) {
        val intent = Intent("ACTION_START_SERVICE")
        sendBroadcast(intent)
    }

}
