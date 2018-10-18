package com.mlabar.android_background_location_service.common.location

import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.mlabar.android_background_location_service.R
import com.mlabar.android_background_location_service.common.MyApplication
import com.mlabar.android_background_location_service.common.extension.checkPermissionAccessFineLocation
import com.mlabar.android_background_location_service.common.googleapi.GoogleApiHelper
import com.mlabar.android_background_location_service.common.repository.LocationRepository


class LocationService : Service() {

    private val TAG = LocationService::class.java.simpleName

    private val UPDATE_INTERVAL = (10 * 1000).toLong()

    private var mLocationRequest: LocationRequest = LocationRequest().apply {
        interval = UPDATE_INTERVAL
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    /**
     * Life cycle
     */

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        if (!checkPermissionAccessFineLocation()) {
            Toast.makeText(this, R.string.permission_not_accepted, Toast.LENGTH_LONG).show()
            return START_NOT_STICKY
        } else {

            LocalBroadcastManager.getInstance(this).let {
                it.registerReceiver(messageReceiver, IntentFilter(GoogleApiHelper.ACTION_GOOGLE_API_CONNECTED))
                it.registerReceiver(messageReceiver, IntentFilter(GoogleApiHelper.ACTION_GOOGLE_API_SUSPENDED))
                it.registerReceiver(messageReceiver, IntentFilter(GoogleApiHelper.ACTION_GOOGLE_API_FAILED))
            }

            MyApplication.instance.googleApiHelper.connect()

            return START_STICKY
        }
    }

    override fun onBind(intent: Intent?) = null

    override fun onDestroy() {
        super.onDestroy()

        MyApplication.instance.googleApiHelper.disconnect()

        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver)

        LocationRepository.isServiceStarting.value = false
    }

    /**
     * Broadcast Manager
     */

    private val messageReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                GoogleApiHelper.ACTION_GOOGLE_API_CONNECTED -> {
                    Toast.makeText(this@LocationService, R.string.google_api_client_connected, Toast.LENGTH_LONG).show()
                    LocationRepository.isServiceStarting.value = true
                    requestLocationUpdates()
                }
                GoogleApiHelper.ACTION_GOOGLE_API_SUSPENDED -> {
                    Toast.makeText(this@LocationService, R.string.google_api_client_connection_suspended, Toast.LENGTH_LONG).show()
                    LocationRepository.isServiceStarting.value = false
                }
                GoogleApiHelper.ACTION_GOOGLE_API_FAILED -> {
                    Toast.makeText(this@LocationService, R.string.google_api_client_connection_failed, Toast.LENGTH_LONG).show()
                    LocationRepository.isServiceStarting.value = false
                }
            }
        }

    }

    /**
     * Methods
     */

    fun requestLocationUpdates() {
        try {
            Log.d(TAG, "Starting location updates")
            val intent = Intent(this, LocationUpdatesBroadcastReceiver::class.java)
            val pendintIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            LocationServices.FusedLocationApi.requestLocationUpdates(MyApplication.instance.googleApiHelper.googleApiClient, mLocationRequest, pendintIntent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

}