package com.mlabar.android_background_location_service.common.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.mlabar.android_background_location_service.common.location.LocationService
class StartServiceReceiver : BroadcastReceiver() {

    private val TAG = StartServiceReceiver::class.java.simpleName

    companion object {
        const val ACTION_START_SERVICE = "ACTION_START_SERVICE"
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d(TAG, "onReceive")

        val service = Intent(context, LocationService::class.java)
        context?.startService(service)

    }

}

