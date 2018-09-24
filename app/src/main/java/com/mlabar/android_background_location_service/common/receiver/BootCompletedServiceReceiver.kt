package com.mlabar.android_background_location_service.common.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mlabar.android_background_location_service.feature.location.LocationService

class BootCompletedServiceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val service = Intent(context, LocationService::class.java)
        context?.startService(service)
    }

}