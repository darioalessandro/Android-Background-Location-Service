package com.mlabar.android_background_location_service.common.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.mlabar.android_background_location_service.common.googleapi.GoogleApiHelper
import com.mlabar.android_background_location_service.common.location.LocationService

class StartStopServiceReceiver : BroadcastReceiver() {

    companion object {
        private val TAG = StartStopServiceReceiver::class.java.simpleName

        const val ACTION_MAIN_SERVICE_START = "ACTION_MAIN_SERVICE_START"
        const val ACTION_MAIN_SERVICE_STOP = "ACTION_MAIN_SERVICE_STOP"
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        intent?.action.let { action ->
            when (action) {
                ACTION_MAIN_SERVICE_START -> {
                    val service = Intent(context, LocationService::class.java)
                    context?.startService(service)
                }
                ACTION_MAIN_SERVICE_STOP -> {
                    val service = Intent(context, LocationService::class.java)
                    context?.stopService(service)
                }
                else -> {
                    Log.d(TAG, "Action not identified: $action")
                }
            }
        }

    }

}

