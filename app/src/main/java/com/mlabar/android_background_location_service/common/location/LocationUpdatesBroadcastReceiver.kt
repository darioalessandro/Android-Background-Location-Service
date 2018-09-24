package com.mlabar.android_background_location_service.common.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.LocationResult

class LocationUpdatesBroadcastReceiver : BroadcastReceiver() {

    private val TAG = LocationUpdatesBroadcastReceiver::class.java.simpleName

    companion object {
        const val ACTION_LOCATION_UPDATES = "ACTION_LOCATION_UPDATES"
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d(TAG, "onReceive")

        context?.let {
            LocationResult.extractResult(intent)?.apply {
                val locationResultHelper = LocationResultHelper(context, locations)
                locationResultHelper.showNotification()
            }
        }

    }

}