package com.mlabar.android_background_location_service.common.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.mlabar.android_background_location_service.common.location.LocationService

class StartStopServiceReceiver : BroadcastReceiver() {

    private val TAG = StartStopServiceReceiver::class.java.simpleName

    companion object {
        const val ACTION_START_SERVICE = "ACTION_START_SERVICE"
        const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d(TAG, "onReceive")

        intent?.action.let {
            when (it) {
                ACTION_START_SERVICE -> {
                    val service = Intent(context, LocationService::class.java)
                    context?.startService(service)
                }
                ACTION_STOP_SERVICE -> {
                    val service = Intent(context, LocationService::class.java)
                    context?.stopService(service)
                }
                else -> { // Note the block
                    Log.d(TAG, "Action not identified")
                }
            }
        }

    }

}

