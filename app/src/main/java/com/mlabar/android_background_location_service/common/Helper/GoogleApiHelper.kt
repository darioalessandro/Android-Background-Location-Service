package com.mlabar.android_background_location_service.common.Helper

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.mlabar.android_background_location_service.common.observer.GoogleApiConnectionObserver

class GoogleApiHelper(context: Context) : GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    companion object {
        private val TAG = GoogleApiHelper::class.java.simpleName
    }

    var googleApiClient: GoogleApiClient

    private var googleApiConnectionObservables: MutableList<GoogleApiConnectionObserver> = mutableListOf()

    init {
        googleApiClient = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
    }

    val isConnected: Boolean
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
        googleApiConnectionObservables.forEach {
            it.onConnected(bundle)
        }
    }

    override fun onConnectionSuspended(i: Int) {
        Log.d(TAG, "onConnectionSuspended: googleApiClient.connect()")
        googleApiConnectionObservables.forEach {
            it.onConnectionSuspended(i)
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d(TAG, "onConnectionFailed: connectionResult = $connectionResult")
        googleApiConnectionObservables.forEach {
            it.onConnectionFailed(connectionResult)
        }
    }

    /**
     * Observer
     */

    fun addOberver(googleApiConnectionObservable: GoogleApiConnectionObserver) {
        if (!googleApiConnectionObservables.contains(googleApiConnectionObservable)) {
            googleApiConnectionObservables.add(googleApiConnectionObservable)
        }
    }

    fun removeOberver(googleApiConnectionObservable: GoogleApiConnectionObserver) {
        val index = googleApiConnectionObservables.indexOf(googleApiConnectionObservable)
        if (index != -1) {
            googleApiConnectionObservables.removeAt(index)
        }
    }

}