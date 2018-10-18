package com.mlabar.android_background_location_service.common.googleapi

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager


class GoogleApiHelper(private val context: Context) : GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    companion object {
        private val TAG = GoogleApiHelper::class.java.simpleName

        const val ACTION_GOOGLE_API_CONNECTED = "ACTION_GOOGLE_API_CONNECTED"
        const val ACTION_GOOGLE_API_SUSPENDED = "ACTION_GOOGLE_API_SUSPENDED"
        const val ACTION_GOOGLE_API_FAILED = "ACTION_GOOGLE_API_FAILED"
    }

    var googleApiClient: GoogleApiClient = GoogleApiClient.Builder(context)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()

    /**
     * Methods
     */

    fun connect() {
        if (!googleApiClient.isConnected) {
            googleApiClient.connect()
        }
    }

    fun disconnect() {
        if (googleApiClient.isConnected) {
            googleApiClient.disconnect()
        }
    }

    /**
     * Listeners
     */

    override fun onConnected(bundle: Bundle?) {
        Log.d(TAG, "onConnected")
        Log.d(TAG, "sendBroadcast -> $ACTION_GOOGLE_API_CONNECTED")
        val intent = Intent(ACTION_GOOGLE_API_CONNECTED)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    override fun onConnectionSuspended(i: Int) {
        Log.d(TAG, "onConnectionSuspended")
        Log.d(TAG, "sendBroadcast -> $ACTION_GOOGLE_API_SUSPENDED")
        val intent = Intent(ACTION_GOOGLE_API_SUSPENDED)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d(TAG, "onConnectionFailed ($connectionResult) ")
        Log.d(TAG, "sendBroadcast -> $ACTION_GOOGLE_API_FAILED")
        val intent = Intent(ACTION_GOOGLE_API_FAILED)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

}