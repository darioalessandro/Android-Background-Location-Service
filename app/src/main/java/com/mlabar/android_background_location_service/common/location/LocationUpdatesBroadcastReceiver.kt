package com.mlabar.android_background_location_service.common.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.common.util.CollectionUtils
import com.google.android.gms.location.LocationResult
import com.mlabar.android_background_location_service.common.repository.LocationRepository

class LocationUpdatesBroadcastReceiver : BroadcastReceiver() {

    private val TAG = LocationUpdatesBroadcastReceiver::class.java.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d(TAG, "onReceive")

        context?.let {

            LocationResult.extractResult(intent)?.apply {

                if (!CollectionUtils.isEmpty(locations)) {

                    LocationRepository.location.value = locations.get(0)
                    LocationResultHelper.showNotification(context, locations)

                }

            }

        }

    }

}