package com.mlabar.android_background_location_service.feature.location

import android.Manifest
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class LocationService : Service(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private val TAG = LocationService::class.java.simpleName

    private val UPDATE_INTERVAL = (10 * 1000).toLong()

    private var mGoogleApiClient: GoogleApiClient? = null
    private var mLocationRequest: LocationRequest = LocationRequest().apply {
        interval = UPDATE_INTERVAL
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    // Service

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand")
        if (!checkPermissions()) {
            Toast.makeText(this, "Permission not accepted !", Toast.LENGTH_LONG).show()
            return START_NOT_STICKY
        } else {
            mGoogleApiClient = GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build()
            mGoogleApiClient?.connect()
            return START_STICKY
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    // Listeners

    override fun onConnected(p0: Bundle?) {
        Toast.makeText(this, "Connected to Google API", Toast.LENGTH_LONG).show()
        requestLocationUpdates()
    }

    override fun onConnectionSuspended(p0: Int) {
        Toast.makeText(this, "Connection suspend to Google API", Toast.LENGTH_LONG).show()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(this, "Connection failed to Google API", Toast.LENGTH_LONG).show()
    }

    fun requestLocationUpdates() {
        try {
            Log.d(TAG, "Starting location updates")
            val intent = Intent(this, LocationUpdatesBroadcastReceiver::class.java)
            val pendintIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, pendintIntent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

}