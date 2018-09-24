package com.mlabar.android_background_location_service.common.location

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.mlabar.android_background_location_service.R
import com.mlabar.android_background_location_service.common.extension.checkPermissionAccessFineLocation

class LocationService : Service(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private val TAG = LocationService::class.java.simpleName

    private val UPDATE_INTERVAL = (10 * 1000).toLong()

    private var mGoogleApiClient: GoogleApiClient? = null

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
            connectGoogleApiClient()
            mGoogleApiClient?.connect()
            return START_STICKY
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    /**
     * Listeners
     */

    override fun onConnected(p0: Bundle?) {
        Toast.makeText(this, R.string.google_api_client_connected, Toast.LENGTH_LONG).show()
        requestLocationUpdates()
    }

    override fun onConnectionSuspended(p0: Int) {
        Toast.makeText(this, R.string.google_api_client_connection_suspended, Toast.LENGTH_LONG).show()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(this, R.string.google_api_client_connection_failed, Toast.LENGTH_LONG).show()
    }

    /**
     * Methods
     */

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

    fun connectGoogleApiClient(){
        if(mGoogleApiClient == null){
            mGoogleApiClient = GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build()
        }
        if(mGoogleApiClient?.isConnected == false) {
            mGoogleApiClient?.connect()
        } else {
            Toast.makeText(this, R.string.google_api_client_already_connected, Toast.LENGTH_LONG).show()
        }
    }

}