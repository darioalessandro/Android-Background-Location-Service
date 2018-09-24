package com.mlabar.android_background_location_service.feature.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.LocationResult

class LocationUpdatesBroadcastReceiver : BroadcastReceiver() {

    private val TAG = LocationUpdatesBroadcastReceiver::class.java.simpleName

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