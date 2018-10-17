package com.mlabar.android_background_location_service.common.googleapi

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices

class GoogleApiHelper(context: Context) : GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    companion object {
        private val TAG = GoogleApiHelper::class.java.simpleName
    }

    var googleApiClient: GoogleApiClient = GoogleApiClient.Builder(context)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()

    private var googleApiConnectionObservers: MutableList<GoogleApiConnectionObserver> = mutableListOf()

    private val isConnected: Boolean
        get() = googleApiClient.isConnected

    /**
     * Methods
     */

    fun connect() {
        if (!isConnected) {
            googleApiClient.connect()
        }
    }

    fun disconnect() {
        if (isConnected) {
            googleApiClient.disconnect()
        }
    }

    /**
     * Listener
     */

    override fun onConnected(bundle: Bundle?) {
        Log.d(TAG, "onConnected: googleApiClient.connect()")
        googleApiConnectionObservers.forEach {
            it.onConnected(bundle)
        }
    }

    override fun onConnectionSuspended(i: Int) {
        Log.d(TAG, "onConnectionSuspended: googleApiClient.connect()")
        googleApiConnectionObservers.forEach {
            it.onConnectionSuspended(i)
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d(TAG, "onConnectionFailed: connectionResult = $connectionResult")
        googleApiConnectionObservers.forEach {
            it.onConnectionFailed(connectionResult)
        }
    }

    /**
     * Observer
     */

    fun addOberver(googleApiConnectionObserver: GoogleApiConnectionObserver) {
        if (!googleApiConnectionObservers.contains(googleApiConnectionObserver)) {
            googleApiConnectionObservers.add(googleApiConnectionObserver)
        }
    }

    fun removeOberver(googleApiConnectionObserver: GoogleApiConnectionObserver) {
        val index = googleApiConnectionObservers.indexOf(googleApiConnectionObserver)
        if (index != -1) {
            googleApiConnectionObservers.removeAt(index)
        }
    }

}